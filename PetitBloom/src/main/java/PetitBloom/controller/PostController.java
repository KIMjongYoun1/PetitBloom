package PetitBloom.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import PetitBloom.Service.CommentService;
import PetitBloom.Service.FileService;
import PetitBloom.Service.PostService;
import PetitBloom.bean.PostVO;
import PetitBloom.utils.SessionUtils;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class PostController {

	private final PostService postService;
	private final FileService fileService;
	private final CommentService commentService;

	@Autowired
	public PostController(PostService postService, FileService fileService, CommentService commentService) {
		this.postService = postService;
		this.fileService = fileService;
		this.commentService= commentService;

	}

	// 게시글 작성 페이지로 이동
	@GetMapping("/post/new")
	public String newPostPage() {
		return "post"; // Post.html 페이지로 리다이렉트
	}

	// 게시글 저장
	@PostMapping("/post")
	@ResponseBody
	public Map<String, String> createPost(PostVO post, @RequestParam("image") MultipartFile image, // 파일 업로드 처리
			HttpSession session) {

		Map<String, String> response = new HashMap<>();
		String userId = SessionUtils.getLoggedInUserId(session); // 세션에서 로그인된 사용자 ID 가져오기

		// 사용자 인증 확인
		if (userId == null) {
			return createErrorResponse(response, "로그인이 필요합니다.", "/login");
		}

		// 입력 데이터 유효성 검사
		if (post.getTitle() == null || post.getTitle().trim().isEmpty()) {
			return createErrorResponse(response, "제목을 입력해주세요.");
		}

		if (post.getContent() == null || post.getContent().trim().isEmpty()) {
			return createErrorResponse(response, "내용을 입력해주세요.");
		}

		log.info("게시글 저장 요청: 사용자 ID={}, 제목={}", userId, post.getTitle());
		if (post.getThumbnail() != null) {
			log.info("썸네일 경로: {}", post.getThumbnail());
		}

		// 파일 업로드 처리
		String thumbnailPath = null; // 초기화
		if (image != null && !image.isEmpty()) {
			try {
				thumbnailPath = validateAndSaveImage(image);
			} catch (IOException e) {
				response.put("status", "error");
				response.put("message", "파일 업로드 중 오류가 발생했습니다: " + e.getMessage());
				return response;
			}
		}
		post.setThumbnail(thumbnailPath); // 한 번만 설정

		// 게시글 저장
		post.setUserId(userId); // 로그인된 사용자의 ID 설정
		post.setCreatedAt(LocalDateTime.now()); // 현재 시간 설정
		post.setUpdatedAt(LocalDateTime.now()); // 현재 시간 설정
		post.setThumbnail(thumbnailPath); // 썸네일 경로 설정

		postService.createPost(post); // 게시글 저장

		response.put("status", "success");
		response.put("redirect", "/profile");
		return response;
	}

	// 에러응답
	private Map<String, String> createErrorResponse(Map<String, String> response, String message,
			String... redirectUrl) {
		response.put("status", "error");
		response.put("message", message);
		if (redirectUrl.length > 0) {
			response.put("redirect", redirectUrl[0]);
		}
		return response;
	}

	private String validateAndSaveImage(MultipartFile file) throws IOException {
		// 파일 크기 제한 (10MB)
		final long MAX_FILE_SIZE = 10 * 1024 * 1024;
		if (file.getSize() > MAX_FILE_SIZE) {
			throw new IOException("파일 크기는 10MB를 초과할 수 없습니다.");
		}

		// MIME 타입 검사
		if (!file.getContentType().startsWith("image/")) {
			throw new IOException("이미지 파일만 업로드 가능합니다.");
		}

		// 확장자 검사
		String originalFileName = file.getOriginalFilename();
		if (!isAllowedExtension(originalFileName)) {
			throw new IOException("허용되지 않은 파일 형식입니다. (jpg, jpeg, png, gif, bmp만 가능)");
		}

		// 파일 저장
		return fileService.saveImage(file); // 저장된 파일 경로 반환
	}

	// 허용된 확장자 검사 메서드
	private boolean isAllowedExtension(String fileName) {
		String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase(); // 확장자 추출
		List<String> allowedExtensions = List.of("jpg", "jpeg", "png", "gif", "bmp"); // 허용 확장자 목록
		return allowedExtensions.contains(extension);
	}

	// 게시글 수정 페이지
	@GetMapping("/post/{id}/edit")
	public String editPost(@PathVariable Long id, Model model) {
		PostVO post = postService.getPostById(id); // 게시글 가져오기

		if (post == null) {
			// 로그: 없는 게시글 ID 요청
			System.out.println("No post found with ID: " + id);
			return "redirect:/404"; // 없는 게시글일 경우 404 페이지로 이동
		}

		model.addAttribute("post", post); // 게시글 데이터 전달
		return "post-edit"; // 게시글 수정 페이지
	}

	// 게시글 수정 처리 (Ajax)
	@PostMapping("/post/{id}/edit")
	@ResponseBody
	public Map<String, Object> updatePost(@PathVariable Long id, @RequestParam("title") String title,
			@RequestParam("content") String content,
			@RequestParam(value = "image", required = false) MultipartFile image, HttpSession session) {

		Map<String, Object> response = new HashMap<>();
		String userId = SessionUtils.getLoggedInUserId(session);
		if (userId == null) {
			response.put("status", "error");
			response.put("message", "로그인이 필요합니다.");
			return response;
		}

		PostVO post = postService.getPostById(id);
		if (post == null || !post.getUserId().equals(userId)) {
			response.put("status", "error");
			response.put("message", "수정 권한이 없습니다.");
			return response;
		}

		log.info("게시글 저장 요청: 사용자 ID={}, 제목={}", userId, post.getTitle());
		if (post.getThumbnail() != null) {
			log.info("썸네일 경로: {}", post.getThumbnail());
		}

		post.setTitle(title);
		post.setContent(content);
		post.setUpdatedAt(LocalDateTime.now());

		// 파일 업로드 처리
		if (image != null && !image.isEmpty()) {
			try {
				String thumbnailPath = validateAndSaveImage(image); // 여기가 위치!
				post.setThumbnail(thumbnailPath); // 썸네일 경로 설정
			} catch (IOException e) {
				response.put("status", "error");
				response.put("message", "파일 업로드 중 오류가 발생했습니다: " + e.getMessage());
				return response;
			}
		}

		postService.updatePost(post);
		response.put("status", "success");
		response.put("post", post); // 수정된 게시글 반환
		return response;
	}

	// 게시글 삭제
	@PostMapping("/post/{id}/delete")
	public String deletePost(@PathVariable Long id) {
		postService.deletePost(id); // 게시글 삭제
		return "redirect:/profile"; // 게시글 목록 페이지로 리다이렉트
	}
	
	// 게시글 상세 페이지
	@GetMapping("/post/{id}")
	public String viewPost(@PathVariable Long id, HttpSession session, Model model) {
	    PostVO post = postService.getPostById(id); // 게시글 조회

	    if (post == null) {
	        return "redirect:/404"; // 게시글이 없으면 404 페이지로 이동
	    }

	    // 현재 로그인된 사용자 정보 가져오기
	    String loggedInUserId = SessionUtils.getLoggedInUserId(session);

	    // 작성자 여부 확인
	    boolean isAuthor = loggedInUserId != null && loggedInUserId.equals(post.getUserId());

	    // 모델에 게시글과 작성자 여부 추가
	    model.addAttribute("post", post);
	    model.addAttribute("isAuthor", isAuthor); // 수정 및 삭제 권한 체크용
	    model.addAttribute("comments", commentService.getCommentsByPostId(id)); // 댓글 리스트 추가model.addAttribute("comments", commentService.getCommentsByPostId(postId)); // 댓글 리스트 추가
	    return "detail"; // detail.html로 이동 (상세 페이지)
	}
	
    // 게시글 상세 보기
    @GetMapping("/garden/{id}")
    public String viewPost(@PathVariable Long id, Model model) {
        System.out.println("Received ID: " + id);  // ID 값 확인 로그
        PostVO post = postService.getPostById(id);  // PostService에서 상세 정보 조회
        if (post == null) {
            System.out.println("Post not found for ID: " + id);  // 게시글이 없을 경우 로그
            return "redirect:/404";  // 게시글이 없으면 404 페이지로 리다이렉트
        }

        model.addAttribute("post", post);  // 게시글 정보를 모델에 추가
        return "detail";  //detail.html 페이지로 전달
    }


}

package PetitBloom.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import PetitBloom.Service.PostService;
import PetitBloom.bean.PostVO;
import PetitBloom.utils.SessionUtils;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 게시글 목록 조회
    @GetMapping("/posts")
    public String getPosts(HttpSession session, Model model) {
        String userId = SessionUtils.getLoggedInUserId(session); // 세션에서 로그인된 사용자 ID 가져오기

        if (userId == null) {
            return "redirect:/login"; // 로그인되지 않은 경우 로그인 페이지로 리다이렉트
        }

        // 게시글 가져오기
        List<PostVO> posts = postService.getPostsByUser(userId);
        model.addAttribute("posts", posts); // 게시글 리스트를 모델에 추가

        return "posts"; // posts.html 페이지로 이동
    }

    // 게시글 작성 페이지로 이동
    @GetMapping("/posts/new")
    public String newPostPage() {
        return "Post"; // Post.html 페이지로 리다이렉트
    }

    // 게시글 저장
    @PostMapping("/posts")
    @ResponseBody
    public Map<String, String> createPost(PostVO post, HttpSession session) {
        String userId = SessionUtils.getLoggedInUserId(session); // 세션에서 로그인된 사용자 ID 가져오기

        if (userId == null) {
            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "로그인이 필요합니다.");
            response.put("redirect", "/login");
            return response;
        }

        post.setUserId(userId); // 로그인된 사용자의 ID 설정
        post.setCreatedAt(LocalDateTime.now()); // 현재 시간 설정
        post.setUpdatedAt(LocalDateTime.now()); // 현재 시간 설정

        postService.createPost(post); // 게시글 저장

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("redirect", "/profile");
        return response;
    }



    // 게시글 수정 페이지
    @GetMapping("/posts/{id}/edit")
    public String editPost(@PathVariable Long id, Model model) {
        // 로그: 요청받은 게시글 ID
        System.out.println("Requested post ID: " + id);

        PostVO post = postService.getPostById(id); // 게시글 ID로 게시글 가져오기
        
        // 로그: 가져온 게시글 데이터 확인
        if (post != null) {
            System.out.println("Post found: " + post);
        } else {
            System.out.println("No post found with ID: " + id);
        }

        model.addAttribute("post", post); // 게시글 데이터 전달

        return "post-edit"; // 게시글 수정 페이지로 이동
    }


    // 게시글 수정 처리 (Ajax)
    @PostMapping("/posts/{id}/edit")
    @ResponseBody
    public String updatePost(@PathVariable Long id, @RequestBody PostVO post, HttpSession session) {
        String userId = SessionUtils.getLoggedInUserId(session);
        if (userId == null || !post.getUserId().equals(userId)) {
            return "redirect:/login";
        }

        post.setId(id);
        post.setUpdatedAt(LocalDateTime.now());
        postService.updatePost(post); // 게시글 수정
        return "success"; // 수정 완료 후 success 응답
    }

    // 게시글 삭제
    @PostMapping("/posts/{id}/delete")
    public String deletePost(@PathVariable Long id) {
        postService.deletePost(id); // 게시글 삭제
        return "redirect:/profile"; // 게시글 목록 페이지로 리다이렉트
    }
}

package PetitBloom.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import PetitBloom.Service.GardenService;
import PetitBloom.Service.PostService; // 추가된 부분
import PetitBloom.bean.GardenVO;
import PetitBloom.utils.SessionUtils;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class GardenController {

	private final GardenService gardenService; // GardenService 의존성 주입
	private final PostService postService; // PostService 의존성 주입

	@GetMapping("/garden") // URL: localhost:10001/garden
	public String gardenPage() {
		// templates/garden.html 렌더링
		return "garden";
	}

	@GetMapping("/garden/posts")
	@ResponseBody
	public List<GardenVO> getGardenPosts() {
		// GardenVO 리스트를 JSON으로 반환
		return gardenService.getAllPosts();
	}

	// 좋아요 추가
	@PostMapping("/garden/{id}/like")
	public ResponseEntity<Map<String, String>> addLike(@PathVariable Long id, HttpSession session) {
		Map<String, String> response = new HashMap<>();
		String userId = SessionUtils.getLoggedInUserId(session);

		if (userId == null) {
			response.put("status", "error");
			response.put("message", "로그인이 필요합니다.");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
		}

		// 중복 좋아요 체크
		if (gardenService.checkLike(userId, id)) {
			response.put("status", "error");
			response.put("message", "이미 좋아요를 눌렀습니다.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		gardenService.addLike(userId, id); // 좋아요 추가
		postService.updateLikeCount(id, true); // 좋아요 수 증가
		
		response.put("status", "success");
		return ResponseEntity.ok(response);
	}



	// 좋아요 상태 확인
	@GetMapping("/garden/{id}/checkLike")
	@ResponseBody
	public boolean checkLike(@PathVariable Long id, HttpSession session) {
		String userId = SessionUtils.getLoggedInUserId(session);

		if (userId == null) {
			return false; // 로그인되지 않은 사용자는 좋아요를 할 수 없음
		}

		return gardenService.checkLike(userId, id); // 해당 게시글에 좋아요 했는지 확인
	}
}

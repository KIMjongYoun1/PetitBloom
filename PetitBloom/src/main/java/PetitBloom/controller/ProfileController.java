package PetitBloom.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import PetitBloom.Service.PostService;
import PetitBloom.bean.PostVO;
import PetitBloom.bean.ProfileVO;
import PetitBloom.utils.SessionUtils;
import jakarta.servlet.http.HttpSession;

@Controller
public class ProfileController {

	@Autowired
	private PostService postService;

	// 프로필 페이지 보여주기
	@GetMapping("/profile")
	public String showProfile(@RequestParam(value = "edit", required = false) Boolean editMode, HttpSession session,
	                          Model model) {
	    ProfileVO user = SessionUtils.getLoggedInUser(session);

	    if (user != null) {
	        model.addAttribute("user", user);
	        model.addAttribute("editMode", editMode != null && editMode);
	        return "profile"; // profile.html 페이지 반환
	    } else {
	        return "redirect:/login"; // 로그인되지 않으면 로그인 페이지로 리다이렉트
	    }
	}
	
	@GetMapping("/profile/posts")
	@ResponseBody
	public List<PostVO> getUserPosts(HttpSession session) {
	    ProfileVO user = SessionUtils.getLoggedInUser(session);

	    if (user != null) {
	        List<PostVO> posts = postService.getPostsByUser(user.getUserId());
	        for (PostVO post : posts) {
	            if (post.getThumbnail() == null || post.getThumbnail().isEmpty()) {
	                post.setThumbnail("/uploads/default-thumbnail.jpg"); // 기본 이미지 설정
	            }
	        }
	        return posts; // 게시글 리스트를 JSON으로 반환
	    } else {
	        return Collections.emptyList(); // 비로그인 상태에서는 빈 리스트 반환
	    }
	}


	// 프로필 수정 처리
	@PostMapping("/profile")
	public String editProfile(ProfileVO updatedProfile, HttpSession session) {
		ProfileVO user = SessionUtils.getLoggedInUser(session);

		if (user != null) {
			user.setUsername(updatedProfile.getUsername());
			user.setEmail(updatedProfile.getEmail());

			// 비밀번호 수정 시 처리 (비밀번호 암호화는 나중에 추가)
			// if (updatedProfile.getPassword() != null &&
			// !updatedProfile.getPassword().isEmpty()) {
			// user.setPassword(updatedProfile.getPassword()); // 나중에 암호화 기능 추가
			// }

			// 세션에 수정된 사용자 정보 저장
			session.setAttribute("loggedInUser", user);
			return "redirect:/profile"; // 프로필 페이지로 리다이렉트
		} else {
			return "redirect:/login"; // 로그인되지 않으면 로그인 페이지로 리다이렉트
		}
	}
}

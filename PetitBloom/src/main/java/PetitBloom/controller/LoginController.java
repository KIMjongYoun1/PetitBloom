package PetitBloom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import PetitBloom.Service.ProfileService;
import PetitBloom.bean.ProfileVO;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class LoginController {

	private final ProfileService profileService;

	@Autowired
	
	public LoginController(ProfileService profileService) {
		this.profileService = profileService;
	}

	@GetMapping("/login")
	public String showLoginPage() {
		return "login"; // login.html 페이지 반환
	}

	@PostMapping("/login")
	 public String login(ProfileVO profile, Model model) {
        // 로그인 처리 (id는 String 타입)
        ProfileVO user = profileService.authenticate(profile.getId(), profile.getPassword());

        if (user != null) {
        	log.info("Login successful for user: " + user.getId());
            return "redirect:/home"; // 로그인 성공 후 홈 페이지로 리다이렉트
        } else {
            
        	log.warn("Invalid credentials for user: " + profile.getId());
            model.addAttribute("error", "Invalid credentials"); // 로그인 실패 시 에러 메시지 표시
            return "login"; // 다시 로그인 페이지로 이동
        }
    }

}

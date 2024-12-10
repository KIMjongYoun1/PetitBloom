package PetitBloom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import PetitBloom.Service.ProfileService;
import PetitBloom.bean.ProfileVO;
import jakarta.servlet.http.HttpSession;
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
    public String login(ProfileVO profile, HttpSession session, Model model) {
        log.info("Received ProfileVO: userId=" + profile.getUserId() + ", password=" + profile.getPassword());

        // 로그인 처리
        ProfileVO user = profileService.authenticate(profile.getUserId(), profile.getPassword());

        if (user != null) {
            log.info("Login successful for user: " + user.getUserId());

            // 세션에 사용자 정보 저장
            session.setAttribute("loggedInUser", user);

            return "redirect:/home"; // 로그인 성공 후 홈 페이지로 리다이렉트
        } else {
            log.warn("Invalid credentials for user: " + profile.getUserId());
            model.addAttribute("error", "Invalid credentials"); // 로그인 실패 시 에러 메시지 표시
            return "login"; // 다시 로그인 페이지로 이동
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return "redirect:/login"; // 로그인 페이지로 리다이렉트
    }
}

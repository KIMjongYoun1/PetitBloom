package PetitBloom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import PetitBloom.bean.ProfileVO;
import PetitBloom.utils.SessionUtils;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {

    @GetMapping("/")
    public String landingPage() {
        return "landing"; // landing.html 페이지 반환
    }
    
    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        ProfileVO user = SessionUtils.getLoggedInUser(session);

        if (user != null) {
            model.addAttribute("user", user);
        }
        return "home";
    }


  
}

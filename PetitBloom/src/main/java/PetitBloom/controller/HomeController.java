package PetitBloom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {
	
	 @GetMapping("/")
	    public String landingPage() {
	        return "landing"; // landing.html 페이지 반환
	    }
	
    @GetMapping("/home")
    public String home() {
        return "home";  // landing.html을 렌더링
    }
}

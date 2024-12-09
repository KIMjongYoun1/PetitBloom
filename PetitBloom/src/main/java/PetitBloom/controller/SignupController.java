package PetitBloom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import PetitBloom.Service.ProfileService;
import PetitBloom.bean.ProfileVO;

@Controller
@RequestMapping("/signup")
public class SignupController {
	
	 	private final ProfileService profileService;

	    @Autowired
	    public SignupController(ProfileService profileService) {
	        this.profileService = profileService;
	    }
	    
	    @GetMapping
		public String showSignupPage() {
			return "signup"; // This will render the signup.html page
		}
	    
	    @PostMapping
	    public String signUp(@ModelAttribute ProfileVO profileVO) {
	        profileService.createUser(profileVO);
	        return "redirect:/login"; // 회원가입 후 로그인 페이지로 리다이렉트
	    }
	
}

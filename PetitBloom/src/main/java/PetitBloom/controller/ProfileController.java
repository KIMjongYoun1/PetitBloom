package PetitBloom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ProfileController {

	@GetMapping("/profile") // URL: localhost:10001/profile
	public void profile() {
		// templates/profile.html 자동 렌더링
	}

}

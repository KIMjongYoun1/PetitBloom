package PetitBloom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class GardenController {

	  @GetMapping("/garden") // URL: localhost:10001/garden
		public void garden() {
	        // templates/garden.html 자동 렌더링
	    }
	
}

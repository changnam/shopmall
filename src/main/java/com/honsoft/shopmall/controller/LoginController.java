package com.honsoft.shopmall.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honsoft.shopmall.dto.SignupRequest;
import com.honsoft.shopmall.service.UserService;

@RequestMapping("/")
@Controller
public class LoginController {
	private static Logger logger = LoggerFactory.getLogger(LoginController.class);

	private final UserService userService;

	public LoginController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/signup")
	public String showSignupForm() {
		return "signup";
	}
	
	@PostMapping("/signup")
	public String processSignupForm(SignupRequest signupRequest, Model m) {
		m.addAttribute("signupRequest",signupRequest);
		return "signupResult";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setDisallowedFields("hobbies");
		String[] disallowedFields = binder.getDisallowedFields();
        if (disallowedFields != null) {
            System.out.println("Disallowed fields: " + String.join(", ", disallowedFields));
        }

        
	}

}

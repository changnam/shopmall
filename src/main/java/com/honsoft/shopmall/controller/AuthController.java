package com.honsoft.shopmall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class AuthController {

	@GetMapping("/access-denied")
	public Object accessDenied() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("exception", new RuntimeException("access denied"));
		modelAndView.setViewName("errorCommon");

		return modelAndView;
	}

}

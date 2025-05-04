package com.honsoft.shopmall.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {


	@GetMapping("/login")
	public String login(HttpServletRequest request) {   
	//	String sessionid = request.getSession(true).getId();
	//	System.out.println(sessionid);
      return "login";        
   }
 
   
	@GetMapping("/loginfailed")
	public String loginerror(Model model) { 
      model.addAttribute("error", "true");   
      return "login";                      
   }   
	
	@GetMapping("/logout")
	public String logout(Model model, HttpServletRequest request) {
		 HttpSession session = request.getSession(false);  // Session이 없으면 null return
	     if(session != null) {
	            session.invalidate();
	      }
		return "login";  
	}
	
	
}

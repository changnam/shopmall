package com.honsoft.shopmall.controller;

import java.security.Principal;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExamController {
	
	@GetMapping("/exam01")
	public String requestMethod(Model model) {		   	  
	   	return "viewPage01";
	}
	
	@GetMapping("/home/main")
	public String requestMethod5(Model model) {				
	    model.addAttribute("data", "homePage.html");	  	  
	   	return "homePage";
	}	
	
	@GetMapping("/member/main")
	public String requestMethod4(Model model) {			  
	   	model.addAttribute("data", "memberPage.html");	  
	   	return "memberPage";
	}
	
	
	@GetMapping("/manager/main")
	public String requestMethod3(Model model) {			
	   	model.addAttribute("data", "managerPage.html");	   
	   	return "managerPage";
	}
	
	@GetMapping("/manager/user")
	public String requestManagerUser(Principal principal,Authentication authentication, @AuthenticationPrincipal UserDetails userDetailsInjection, Model m) {
		
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		
		String username = principal.getName();
		String username2 = authentication.getName();
		String username3 = userDetails.getUsername();
		String password = userDetails.getPassword();
		String authority = userDetails.getAuthorities().toString();
		String authority2= userDetailsInjection.getAuthorities().toString();
		
		m.addAttribute("username",username);
		m.addAttribute("username2",username2);
		m.addAttribute("username3",username3);
		m.addAttribute("password",password);
		m.addAttribute("authority",authority);
		m.addAttribute("authority2",authority2);
		
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication2 = context.getAuthentication();
		String username4 = authentication2.getName();
		Object principal2 = authentication2.getPrincipal();
		Collection<? extends GrantedAuthority> authorities = authentication2.getAuthorities();
		
		String authority3 = authorities.toString();
		
		m.addAttribute("username4",username4);
		m.addAttribute("authority3",authority3);
		
		return "managerUserPage";
	}
	
	@GetMapping("/admin/main")
	public String requestMethod2(Model model) {			
	   	model.addAttribute("data", "adminPage.html");	 
	   	
	   	 
	 /*  	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	   	UserDetails userDetails = (UserDetails)principal;

	   	
	   	
	   	String username = userDetails.getUsername();
	   	String password = userDetails.getPassword();
	   	String role = userDetails.getAuthorities().toString();
	   	
	   	model.addAttribute("data2", username);	
	   	model.addAttribute("data3", password);	
	   	model.addAttribute("data4", role);	
	   	*/
	   	return "adminPage";
	}

}

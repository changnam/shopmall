package com.honsoft.shopmall.controller;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honsoft.shopmall.entity.Member;
import com.honsoft.shopmall.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

	private static Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	private final MemberService memberService;	
	
	public HomeController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@RequestMapping("/")
	public String welcome(Model model, Authentication authentication, HttpServletRequest httpServletRequest) {

		if (authentication==null) {
			logger.info("@@@@@@@@@@@@@@@@@@@@ authentication is null @@@@@@@@@@@@@@@@@");
			return "welcome";
		}
			
		  User user = (User) authentication.getPrincipal();
		  String userId = user.getUsername();		
		
		  if(userId==null)
			
			  return "redirect:/login";
		 
		
		Member member = memberService.getMemberById(userId);
		
	
		// 세션을 생성하기 전에 기존의 세션 파기
		//httpServletRequest.getSession().invalidate();
        HttpSession session = httpServletRequest.getSession(true);        
		session.setAttribute("userLoginInfo", member);
		return "welcome";
	}
	
}

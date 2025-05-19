package com.honsoft.shopmall.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.honsoft.shopmall.dto.MemberDto;
import com.honsoft.shopmall.entity.Member;
import com.honsoft.shopmall.mapper.MemberMapper;
import com.honsoft.shopmall.repository.MemberRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class CustomSavedRequestAwareAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	
	private final MemberRepository memberRepository;
	private final MemberMapper memberMapper;
	
    public CustomSavedRequestAwareAuthenticationSuccessHandler(MemberRepository memberRepository, MemberMapper memberMapper) {
        // Set default target URL if no saved request is present
        setDefaultTargetUrl("/home");
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
                                        throws ServletException, IOException {
        // Custom logic (e.g., logging)
//        System.out.println(">>> Login successful for: " + authentication.getName());

        // Continue with default behavior (redirect to saved request or default target URL)
    	
    	// Example: Set a session attribute
        HttpSession session = request.getSession();
        
        session.setAttribute("username", authentication.getName());

        // You can also access roles or other user details
        session.setAttribute("roles", authentication.getAuthorities());
        
        Member member = memberRepository.findByMemberId(authentication.getName());
        MemberDto memberDto = memberMapper.toDto(member);
        session.setAttribute("userLoginInfo", memberDto);
        
        super.onAuthenticationSuccess(request, response, authentication);
    }
}

package com.honsoft.shopmall.security;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.honsoft.shopmall.dto.MemberDto;
import com.honsoft.shopmall.entity.Member;
import com.honsoft.shopmall.mapper.MemberMapper;
import com.honsoft.shopmall.repository.MemberRepository;
import com.honsoft.shopmall.service.JwtName;
import com.honsoft.shopmall.service.JwtService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class CustomSavedRequestAwareAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	private final MemberRepository memberRepository;
	private final MemberMapper memberMapper;

	private final JwtService jwtService; // your service to create JWT tokens

	public CustomSavedRequestAwareAuthenticationSuccessHandler(MemberRepository memberRepository,
			MemberMapper memberMapper, JwtService jwtService) {
		// Set default target URL if no saved request is present
		setDefaultTargetUrl("/home");
		this.memberRepository = memberRepository;
		this.memberMapper = memberMapper;
		this.jwtService = jwtService;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		// Custom logic (e.g., logging)
//        System.out.println(">>> Login successful for: " + authentication.getName());

		// Continue with default behavior (redirect to saved request or default target
		// URL)

		// Example: Set a session attribute
		HttpSession session = request.getSession();

		session.setAttribute("username", authentication.getName());

		// You can also access roles or other user details
		session.setAttribute("roles", authentication.getAuthorities());

		Member member = memberRepository.findByMemberId(authentication.getName());
		MemberDto memberDto = memberMapper.toDto(member);
		session.setAttribute("userLoginInfo", memberDto);

		String token = jwtService.generateTokenForAjax(authentication.getName(), getAuthorityNames(authentication)); // or
		Cookie jwtCookie = new Cookie(JwtName.accessToken.name(), token);
		jwtCookie.setHttpOnly(true);
		jwtCookie.setSecure(false); // use false if you’re on http (not recommended for production)
		jwtCookie.setPath("/");
		jwtCookie.setMaxAge(24 * 60 * 60); // 1 day expiration

		response.addCookie(jwtCookie);

		super.onAuthenticationSuccess(request, response, authentication);
	}

	public List<String> getAuthorityNames(Authentication authentication) {
		return authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());
	}
}

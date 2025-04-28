package com.honsoft.shopmall.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

public class CustomLocaleResolver implements LocaleResolver {

	private final AntPathMatcher pathMatcher = new AntPathMatcher();

	@Override
	public Locale resolveLocale(HttpServletRequest request) {
		String uri = request.getRequestURI();

		if (uri.startsWith("/api")) {
			// API는 Accept-Language 기반
			String acceptLanguage = request.getHeader("Accept-Language");
			return (acceptLanguage == null || acceptLanguage.isEmpty()) ? Locale.ENGLISH
					: Locale.forLanguageTag(acceptLanguage);
		} else {
			// 웹은 세션 또는 쿠키 기반 (고정 한국어 예시)
			return Locale.KOREAN;
		}
	}

	@Override
	public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
		// 동적 변경 안 할 거면 비워둬도 됨
	}
}

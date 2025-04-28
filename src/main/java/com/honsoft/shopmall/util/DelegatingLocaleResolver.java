package com.honsoft.shopmall.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

public class DelegatingLocaleResolver implements LocaleResolver {

	private final SessionLocaleResolver sessionLocaleResolver;
	private final AcceptHeaderLocaleResolver acceptHeaderLocaleResolver;

	public DelegatingLocaleResolver(SessionLocaleResolver sessionLocaleResolver,
			AcceptHeaderLocaleResolver acceptHeaderLocaleResolver) {
		this.sessionLocaleResolver = sessionLocaleResolver;
		this.acceptHeaderLocaleResolver = acceptHeaderLocaleResolver;
	}

	@Override
	public Locale resolveLocale(HttpServletRequest request) {
		Object handler = request.getAttribute(HandlerMapping.BEST_MATCHING_HANDLER_ATTRIBUTE);

		if (handler instanceof HandlerMethod handlerMethod) {
			if (handlerMethod.getBeanType().isAnnotationPresent(RestController.class)) {
				// RestController면 API 처리: Accept-Language 헤더
				return acceptHeaderLocaleResolver.resolveLocale(request);
			} else {
				// 일반 Controller면 Session 처리
				return sessionLocaleResolver.resolveLocale(request);
			}
		}

		// 핸들러 못 찾으면 기본 세션 처리
		return sessionLocaleResolver.resolveLocale(request);
	}

	@Override
	public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
		Object handler = request.getAttribute(HandlerMapping.BEST_MATCHING_HANDLER_ATTRIBUTE);

		if (handler instanceof HandlerMethod handlerMethod) {
			if (handlerMethod.getBeanType().isAnnotationPresent(RestController.class)) {
				acceptHeaderLocaleResolver.setLocale(request, response, locale);
			} else {
				sessionLocaleResolver.setLocale(request, response, locale);
			}
		}
	}
}

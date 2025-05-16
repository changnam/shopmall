package com.honsoft.shopmall.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.honsoft.shopmall.entity.BizExceptionMessage;
import com.honsoft.shopmall.exception.BizException;
import com.honsoft.shopmall.repository.BizExceptionMessageRepository;

@Service
public class BizExceptionMessageService {
	private static final Logger logger = LoggerFactory.getLogger(BizExceptionMessageService.class);

	private final BizExceptionMessageRepository repository;

	public BizExceptionMessageService(BizExceptionMessageRepository repository) {
		this.repository = repository;
	}

	public BizException get(String code, String locale) {
		BizExceptionMessage message = repository.findByCodeAndLocale(code, locale)
				.orElseGet(() -> repository.findByCodeAndLocale(code, "en") // fallback to English
						.orElseThrow(() -> new BizException(code, "code not found", 500)));

		return new BizException(code, message.getMessage(), 400); // or get status from another table
	}

	public BizException createLocalizedException(String code) {
		String locale = LocaleContextHolder.getLocale().getLanguage();
		logger.info("locale: {}", locale);
		BizExceptionMessage message = repository.findByCodeAndLocale(code, locale)
				.orElseGet(() -> repository.findByCodeAndLocale(code, "en") // fallback to English
						.orElseThrow(() -> new BizException(code, "code not found", 500)));

		return new BizException(code, message.getMessage(), 400); // or custom status
	}
}

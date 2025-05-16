package com.honsoft.shopmall.service;

import org.springframework.stereotype.Service;

import com.honsoft.shopmall.entity.ExceptionMessage;
import com.honsoft.shopmall.exception.BizException;
import com.honsoft.shopmall.repository.ExceptionMessageRepository;

@Service
public class ExceptionMessageService {

	private final ExceptionMessageRepository repository;

	public ExceptionMessageService(ExceptionMessageRepository repository) {
		this.repository = repository;
	}

	public BizException getException(String code) {
		ExceptionMessage exceptionMessage = repository.findById(code)
				.orElseThrow(() -> new BizException(code, "code not found", 500));
		return new BizException(code, exceptionMessage.getMessage(), exceptionMessage.getHttpStatus());
	}
}

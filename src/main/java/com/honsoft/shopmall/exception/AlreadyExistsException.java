package com.honsoft.shopmall.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="찾을 수 없습니다")
public class AlreadyExistsException extends RuntimeException{

	public AlreadyExistsException(String message) {
		super(message);
		System.out.print(message);
	}
	
}

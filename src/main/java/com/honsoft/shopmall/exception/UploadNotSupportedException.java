package com.honsoft.shopmall.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="지원하지 않는 파일 형식입니다")
public class UploadNotSupportedException extends RuntimeException{

	public UploadNotSupportedException(String message) {
		super(message);
//		System.out.print(message);
	}
	
}

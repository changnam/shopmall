package com.honsoft.shopmall.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BizException extends RuntimeException{
	private String message;
	private int code;
	
}

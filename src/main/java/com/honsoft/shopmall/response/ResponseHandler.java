package com.honsoft.shopmall.response;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHandler {
	public static ResponseEntity<Object> responseBuilder(String message, HttpStatus httpStatus, Object responseObject) {

		
		int statusCode = ((HttpStatus) httpStatus).value();
        String statusName = ((HttpStatus) httpStatus).name();
		
		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		
		if (httpStatus == HttpStatus.OK)
			response.put("success", "success");
		else
			response.put("error", "error");

		response.put("data", responseObject);

		return new ResponseEntity<>(response, HttpStatus.valueOf(statusCode));
	}

}

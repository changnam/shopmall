package com.honsoft.shopmall.exception;

public enum BizExceptions {
	REVIEW_NOT_REGISTERED("Review not registered", 400), REVIEW_PRODUCT_NOT_FOUND("Product Not Found for Review", 404),
	REVIEW_NOT_FOUND("Review Not Found", 404);

	private final BizException bizException;

	BizExceptions(String message, int code) {
		bizException = new BizException(message, code);
	}

	public BizException get() {
		return bizException;
	}
}

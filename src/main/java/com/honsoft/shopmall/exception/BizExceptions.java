package com.honsoft.shopmall.exception;

public enum BizExceptions {
	REVIEW_NOT_REGISTERED("REVIEW_NOT_REGISTERED", "Review not registered", 400),
	REVIEW_PRODUCT_NOT_FOUND("REVIEW_PRODUCT_NOT_FOUND", "Product Not Found for Review", 404),
	REVIEW_NOT_FOUND("REVIEW_NOT_FOUND", "Review Not Found", 404);

	private final BizException bizException;

	BizExceptions(String code, String message, int staus) {
		bizException = new BizException(code, message, staus);
	}

	public BizException get() {
		return bizException;
	}
}

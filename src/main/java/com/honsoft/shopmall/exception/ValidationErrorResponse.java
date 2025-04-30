package com.honsoft.shopmall.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ValidationErrorResponse(LocalDateTime timestamp, Integer status, List<FieldErrorDetail> errors) {

}

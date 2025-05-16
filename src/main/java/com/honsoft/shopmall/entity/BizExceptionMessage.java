package com.honsoft.shopmall.entity;

import java.util.Locale;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "biz_exception_messages")
@NoArgsConstructor
@AllArgsConstructor
@IdClass(BizExceptionMessageId.class)
public class BizExceptionMessage {

	@Id
	private String code;
	@Id
	private String locale;
	
	private String message;
}

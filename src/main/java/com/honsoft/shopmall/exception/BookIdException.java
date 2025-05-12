package com.honsoft.shopmall.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = false)
public class BookIdException extends RuntimeException {

	private String bookId;

	public BookIdException(String bookId) { //생성자
		this.bookId = bookId;
	}

	/*public String getBookId() {  //Getter() 메소드
		return bookId;
	}
	*/
}

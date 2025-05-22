package com.honsoft.shopmall.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {
	private Long authorId;
	private String name;
	private int age;
	private String genre;

//	@Builder.Default
	private List<BookDto> books ;;
}

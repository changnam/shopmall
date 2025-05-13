package com.honsoft.shopmall.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.honsoft.shopmall.dto.AuthorDto;
import com.honsoft.shopmall.entity.Author;

@Mapper(componentModel = "spring") //List<Book> books -> List<BookDto> books 로 전환할때 사용함. 자신이 모르는 타입을 만나면 uses 로 설정된 맵퍼 호출
public interface AuthorMapper {

//	@Autowired
//	@Lazy
//	protected BookMapper bookMapper;
	
	@Mapping(target = "books", ignore = true) // prevent recursion (AuthorDto 의 books 속성을 기본으로 둠)
	AuthorDto toDto(Author author);

	@Mapping(target = "books", ignore = true) // Author 의 books 속성을 기본으로 둠
    Author toEntity(AuthorDto dto);

    List<AuthorDto> toDtoList(List<Author> authors);

    List<Author> toEntityList(List<AuthorDto> authorDtos);
}

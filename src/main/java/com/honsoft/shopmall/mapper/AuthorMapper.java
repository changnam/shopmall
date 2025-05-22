package com.honsoft.shopmall.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import com.honsoft.shopmall.dto.AuthorDto;
import com.honsoft.shopmall.entity.Author;

@Mapper(componentModel = "spring", uses = BookMapper.class) //List<Book> books -> List<BookDto> books 로 전환할때 사용함. 자신이 모르는 타입을 만나면 uses 로 설정된 맵퍼 호출
public abstract class AuthorMapper {

//	@Autowired
//	@Lazy
//	protected BookMapper bookMapper;
	
//	@Mapping(target = "books", ignore = true) // prevent recursion (AuthorDto 의 books 속성을 기본으로 둠)
	public abstract AuthorDto toDto(Author author);

	@Mapping(target = "books", ignore = true) // Author 의 books 속성을 기본으로 둠
	public abstract Author toEntity(AuthorDto dto);

	public abstract List<AuthorDto> toDtoList(List<Author> authors);

    public abstract List<Author> toEntityList(List<AuthorDto> authorDtos);
    
    public Page<AuthorDto> toPage(Page<Author> page){
    	return page.map(author -> toDto(author));
    }
	
//	@AfterMapping
//	public void afterToDto(Author author)
}

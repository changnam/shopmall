package com.honsoft.shopmall.mapper;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import com.honsoft.shopmall.dto.BookDto;
import com.honsoft.shopmall.entity.Author;
import com.honsoft.shopmall.entity.Book;
import com.honsoft.shopmall.repository.AuthorRepository;

import jakarta.persistence.EntityNotFoundException;

@Mapper(componentModel = "spring")
public abstract class BookMapper {
//public abstract class BookMapper {  //객체와 flat 간 변환시 적용
//	@Autowired
//	@Lazy
//	protected AuthorMapper authorMapper;
	

    @Autowired
    protected AuthorRepository authorRepository;

//    @Mapping(source = "author.id", target = "authorId")  //Book 은 Author 객체사용, BookDto 는 String 사용시 전환
//    public abstract BookDto toDto(Book book);

//    @Mapping(source = "authorId", target = "author.id") // We populate author manually
//    public abstract Book toEntity(BookDto dto);

    // Set the actual Author object after mapping
//    @AfterMapping
//    protected void loadAuthor(BookDto dto, @MappingTarget Book book) {
//        if (dto.getAuthorId() != null) {
//            Author author = authorRepository.findById(dto.getAuthorId())
//                .orElseThrow(() -> new IllegalArgumentException("Invalid authorId: " + dto.getAuthorId()));
//            book.setAuthor(author);
//        }
//    }
	
	@Mapping(source = "author.authorId", target="authorId")
	public abstract BookDto toDto(Book book);
	
//	@Mapping(source = "authorId", target="author")
	@Mapping(target = "author", ignore = true)
	@Mapping(target = "bookImage", ignore = true)
	public abstract Book toEntity(BookDto bookDto);
	
	public abstract List<BookDto> toDtoList(List<Book> books);
	
	public abstract List<Book> toEntityList(List<BookDto> bookDtos);
	
	@AfterMapping
	public void afterToEntity(BookDto bookDto, @MappingTarget Book book) {
		Author author = authorRepository.findById(bookDto.getAuthorId()).orElseThrow(() -> new EntityNotFoundException(bookDto.getAuthorId() + " not found"));
		book.setAuthor(author);		
	}
	
}


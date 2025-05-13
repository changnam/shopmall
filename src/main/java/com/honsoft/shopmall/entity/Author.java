package com.honsoft.shopmall.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.honsoft.shopmall.dto.AuthorDto;
import com.honsoft.shopmall.dto.BookDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "authors")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = { "books" })
public class Author {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String name;
	private String genre;
	private Integer age;

	@Builder.Default
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "eauthor", orphanRemoval = true)
	@JsonManagedReference
	private List<Book> books = new ArrayList<>();

	public void addBook(Book book) {
		this.books.add(book);
		book.setEauthor(this);
	}

	public void removeBook(Book book) {
		book.setEauthor(null);
		this.books.remove(book);
	}

	public void removeBooks() {
		this.books.removeIf(book -> {
			book.setEauthor(null);
			return true;
		});
	}

	public AuthorDto toDto() {
		AuthorDto dto = new AuthorDto();
		dto.setId(this.getId());
		dto.setName(this.getName());

		List<BookDto> books = this.getBooks().stream().map(book -> {
			BookDto bd = new BookDto();
			bd.setBookId(book.getBookId());
			bd.setTitle(book.getTitle());
			return bd;
		}).collect(Collectors.toList());

		dto.setBooks(books);
		return dto;
	}

	public static Author toEntity(AuthorDto dto) {
		Author author = new Author();
		author.setName(dto.getName()); // dto 의 속성을 entity 속성에 대입

		List<Book> books = dto.getBooks().stream().map(bookDto -> {
			Book b = new Book();
			b.setTitle(bookDto.getTitle());
			b.setEauthor(author); // important for bidirectional setup
			return b;
		}).collect(Collectors.toList());

		author.setBooks(books);
		return author;
	}

}

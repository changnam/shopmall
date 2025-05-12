package com.honsoft.shopmall.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

	@Column(unique = true,nullable = false)
	private String name;
	private String genre;
	private Integer age;

	@Builder.Default
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "eauthor", orphanRemoval = true)
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

}

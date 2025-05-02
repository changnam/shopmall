package com.honsoft.shopmall;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

import com.honsoft.shopmall.entity.Book;
import com.honsoft.shopmall.entity.Person;
import com.honsoft.shopmall.repository.BookRepository;
import com.honsoft.shopmall.repository.PersonRepository;

import jakarta.annotation.PostConstruct;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class,DataSourceAutoConfiguration.class,UserDetailsServiceAutoConfiguration.class})
public class ShopmallApplication implements CommandLineRunner{

	private final BookRepository bookRepository;
	private final PersonRepository personRepository;
	
	public ShopmallApplication(BookRepository bookRepository,PersonRepository personRepository) {
		this.bookRepository = bookRepository;
		this.personRepository = personRepository;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ShopmallApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		Book book1 = new Book();
		book1.setBookId("ISBN1234");
		book1.setName ("자바스크립트 입문");
		book1.setUnitPrice(new BigDecimal(30000));
		book1.setAuthor("조현영");
		book1.setDescription(
				"자바스크립트의 기초부터 심화까지 핵심 문법을 학습한 후 12가지 프로그램을 만들며 학습한 내용을 확인할 수 있습니다. 문법 학습과 실습이 적절히 섞여 있어 프로그램을 만드는 방법을 재미있게 익힐 수 있습니다.");
		book1.setPublisher("길벗");
		book1.setCategory("IT전문서");
		book1.setUnitsInStock(1000);
		book1.setReleaseDate(LocalDate.of(2024, 2, 20));
		book1.setFileName("ISBN1234.jpg");
		
		Book book2 = new Book();
		book2.setBookId("ISBN1235");
		book2.setName ("파이썬의 정석");
		book2.setUnitPrice(new BigDecimal(29800));
		book2.setAuthor("조용주,임좌상");
		book2.setDescription(
				"4차 산업혁명의 핵심인 머신러닝, 사물 인터넷(IoT), 데이터 분석 등 다양한 분야에 활용되는 직관적이고 간결한 문법의 파이썬 프로그래밍 언어를 최신 트렌드에 맞게 예제 중심으로 학습할 수 있습니다.");
		book2.setPublisher("길벗");
		book2.setCategory("IT교육교재");
		book2.setUnitsInStock(1000);
		book2.setReleaseDate(LocalDate.of(2023, 1, 10));
		book2.setFileName("ISBN1235.jpg");
		
		Book book3 = new Book();
		book3.setBookId("ISBN1236");
		book3.setName ("안드로이드 프로그래밍");
		book3.setUnitPrice(new BigDecimal(36000));
		book3.setAuthor("송미영");
		book3.setDescription(
				"안드로이드의 기본 개념을 체계적으로 익히고, 이를 실습 예제를 통해 익힙니다. 기본 개념과 사용법을 스스로 실전에 적용하는 방법을 학습한 다음 실습 예제와 응용 예제를 통해 실전 프로젝트 응용력을 키웁니다.");
		book3.setPublisher("길벗");
		book3.setCategory("IT교육교재");
		book3.setUnitsInStock(1000);
		book3.setReleaseDate(LocalDate.of(2023, 6, 30));
		book3.setFileName("ISBN1236.jpg");
//		
		bookRepository.save(book1);
		bookRepository.save(book2);
		bookRepository.save(book3);
		
		Person person = new Person();
		person.setName("홍길동");
		person.setPassword("1234");
		
		personRepository.save(person);
	}

	
	@PostConstruct
	public void init() {
	    System.out.println("🔄 Application restarted at " + Instant.now());
	}

}

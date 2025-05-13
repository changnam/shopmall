package com.honsoft.shopmall.restcontroller;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.honsoft.shopmall.dto.BoardFormDto;
import com.honsoft.shopmall.dto.MemberFormDto;
import com.honsoft.shopmall.entity.Book;
import com.honsoft.shopmall.exception.UserException;
import com.honsoft.shopmall.response.ResponseHandler;
import com.honsoft.shopmall.service.BookService;

import jakarta.servlet.ServletContext;

@RestController
@RequestMapping("/api")
public class ReactController {
	private static Logger logger = LoggerFactory.getLogger(ReactController.class);
	
	private final BookService bookService;
	
	@Autowired
    private ApplicationContext applicationContext;

	public ReactController(@Qualifier("bookServiceManualImpl") BookService bookService) {
		this.bookService = bookService;
	}

	@CrossOrigin(origins = "http://localhost:3000") // ✅ Allows only this frontend
	@PostMapping("/register")
	public ResponseEntity<Object> registerUser(@Validated @RequestBody MemberFormDto member,
			BindingResult bindingResult) {
		logger.info("member: " + member.toString());
		if (bindingResult.hasErrors()) {
			logger.info("bindingerror개수: " + bindingResult.getErrorCount());
			throw new UserException("binding에러 발생"); // or return custom error response
		}
		return ResponseHandler.responseBuilder("user saved", HttpStatus.OK,
				BoardFormDto.builder().title("테스트").build());

	}
	
	
	@CrossOrigin(origins = "http://localhost:3000") // ✅ Allows only this frontend
	@GetMapping("/bookstest")
	public ResponseEntity<Object> getBooks() {
		List<Book> list = bookService.getAllBookList();
		if( Math.random() > 0.5)
			throw new UserException("blabla");
		return ResponseHandler.responseBuilder("user saved", HttpStatus.OK,
				list);

	}
	
	@CrossOrigin(origins = "http://localhost:3000") // ✅ Allows only this frontend
	@GetMapping("/listbeans")
	public ResponseEntity<Object> getListOfBeans() {
		
		
        
		String[] beanNames = applicationContext.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName + " -> " + applicationContext.getBean(beanName).getClass().getName());
        }
		return ResponseHandler.responseBuilder("user saved", HttpStatus.OK,
				beanNames);

	}
	
}

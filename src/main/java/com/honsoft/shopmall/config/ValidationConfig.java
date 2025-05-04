package com.honsoft.shopmall.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.honsoft.shopmall.validator.BookValidator;
import com.honsoft.shopmall.validator.UnitsInStockValidator;


@Configuration
public class ValidationConfig {
	
	@Autowired
	UnitsInStockValidator unitsInStockValidator;
	
	
	@Bean
    public BookValidator bookValidator() {
		BookValidator bookValidator = new BookValidator();       
		
		bookValidator.springValidators.add(unitsInStockValidator);
		
		
        return bookValidator;
    }
	

}

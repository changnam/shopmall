package com.honsoft.shopmall.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honsoft.shopmall.dto.ProductRequest;
import com.honsoft.shopmall.util.ProductRequestValidator;

@Controller
@RequestMapping("/products")
public class ProductController {

	private final Validator defaultValidator;
	private final ProductRequestValidator productRequestValidator;

	public ProductController(@Qualifier("validator") Validator defaultValidator,ProductRequestValidator productRequestValidator) {
		this.defaultValidator = defaultValidator;
		this.productRequestValidator = productRequestValidator;
	}

	@GetMapping("/add")
	public String requestProductAddForm(Model m) {
		m.addAttribute("product", new ProductRequest());
		return "products/productAdd";
	}

	@PostMapping("/add")
	public String requestProductAdd(@Validated @ModelAttribute("product") ProductRequest productRequest,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "products/productAdd";
		}

		return "products/productList";
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(defaultValidator,productRequestValidator);
	}
}

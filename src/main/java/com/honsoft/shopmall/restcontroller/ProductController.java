package com.honsoft.shopmall.restcontroller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.honsoft.shopmall.dto.ProductDto;
import com.honsoft.shopmall.response.ResponseHandler;
import com.honsoft.shopmall.service.ProductService;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
	
	private final ProductService productService;
	
	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	@GetMapping
	public ResponseEntity<Object> getAllProducts(){
		List<ProductDto> list = productService.getAllProducts();
		return ResponseHandler.responseBuilder("get all products", HttpStatus.OK, list);
	}

	@GetMapping("/page")
	public ResponseEntity<Object> getPageProducts(
			@PageableDefault(page = 0, size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
		Page<ProductDto> page = productService.getPageProducts(pageable);
		return ResponseHandler.responseBuilder("ok", HttpStatus.OK, page);
	}
	
	@PutMapping
	public ResponseEntity<Object> updateProduct(@RequestBody ProductDto productDto){
		ProductDto updated = productService.updateProduct(productDto.getProductId(), productDto);
		return ResponseHandler.responseBuilder("update success", HttpStatus.OK, updated);
	}
}

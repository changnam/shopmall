package com.honsoft.shopmall.restcontroller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.honsoft.shopmall.dto.ProductDetailDto;
import com.honsoft.shopmall.response.ResponseHandler;
import com.honsoft.shopmall.service.ProductDetailService;

@RestController
@RequestMapping("/api/v1/productdetails")
public class ProductDetailController {
	
	private final ProductDetailService productDetailService;
	
	public ProductDetailController(ProductDetailService productDetailService) {
		this.productDetailService = productDetailService;
	}
	
	@GetMapping
	public ResponseEntity<Object> getAllProducts(){
		List<ProductDetailDto> list = productDetailService.getAllProductDetails();
		return ResponseHandler.responseBuilder("get all products", HttpStatus.OK, list);
	}

//	@GetMapping("/page")
//	public ResponseEntity<Object> getPageProducts(
//			@PageableDefault(page = 0, size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
//		Page<ProductDto> page = productService.getPageProducts(pageable);
//		return ResponseHandler.responseBuilder("ok", HttpStatus.OK, page);
//	}
//	
//	@PutMapping
//	public ResponseEntity<Object> updateProduct(@RequestBody ProductDto productDto){
//		ProductDto updated = productService.updateProduct(productDto.getProductId(), productDto);
//		return ResponseHandler.responseBuilder("update success", HttpStatus.OK, updated);
//	}
//	
//	@DeleteMapping("/delete/{id}")
//	public ResponseEntity<Object> deleteProductById(@PathVariable("id") Long productId){
//		productService.deleteProduct(productId);
//		return ResponseHandler.responseBuilder(productId+" delete success", HttpStatus.OK, productId);
//	}
}

package com.honsoft.shopmall.entity;

import com.honsoft.shopmall.dto.ProductDetailRequest;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "product_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String description;
	private Float weight;
	private Float height;
	private Float width;
	
	@OneToOne(mappedBy = "productDetail")
	private Product product;
	
	public ProductDetail(Float weight,Float height) {
		this.weight = weight;
		this.height = height;
	}
	
	public static ProductDetail from(ProductDetailRequest productDetailRequest) {
		return new ProductDetail(productDetailRequest.weight(), productDetailRequest.height());
	}

}

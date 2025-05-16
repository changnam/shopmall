package com.honsoft.shopmall.dto;

import java.math.BigDecimal;
import java.util.List;

import com.honsoft.shopmall.entity.Review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto extends BaseEntityDto {
    protected Long productId;
    protected String name;
    protected BigDecimal unitPrice;
    protected Integer unitsInStock;
    protected String category;
    protected String productCondition;

    protected ProductDetailDto productDetail;

    protected List<ProductImageDto> images;
    
    protected List<ReviewDto> reviews;
}


package com.honsoft.shopmall.entity;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Inheritance(strategy = InheritanceType.JOINED) // Or SINGLE_TABLE / TABLE_PER_CLASS
//@MappedSuperclass
@Setter
@Getter
@Table(name="products")
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
public class Product extends BaseEntity<String>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    private String name;
    private BigDecimal unitPrice;
    private Integer unitsInStock;
    private String category;
    private String productCondition;
    
    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @JsonManagedReference
    private ProductDetail productDetail;
    // Common fields, e.g., image, condition, etc.
    
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "product_images",joinColumns = @JoinColumn(name = "product_id"))
    private List<ProductImage> images;
    
    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference
    private List<Review> reviews;
    
    public void addImage(String fileName) {
    	ProductImage productImage = new ProductImage();
    	productImage.setFileName(fileName);
    	productImage.setIdx(images.size());
    	images.add(productImage);
    }
    
    public void clearImages() {
    	images.clear();
    }
   
}

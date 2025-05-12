package com.honsoft.shopmall.entity;

import java.math.BigDecimal;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Inheritance(strategy = InheritanceType.JOINED) // Or SINGLE_TABLE / TABLE_PER_CLASS
//@MappedSuperclass
@Data
@Table(name="products")
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
public abstract class Product extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private BigDecimal unitPrice;
    private Integer unitsInStock;
    private String category;
    private String productCondition;
    
    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private ProductDetail productDetail;
    // Common fields, e.g., image, condition, etc.
}

package com.honsoft.shopmall.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="computers")
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Computer extends Product {
	@Column(unique = true, nullable = false)
	private String computerId;
    private String brand;
    private String processor;
    private String ram;
    private String storage;
    
}

package com.honsoft.shopmall.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="computers")
@Data
public class Computer extends Product {
	@Column(unique = true, nullable = false)
	private String computerId;
    private String brand;
    private String processor;
    private String ram;
    private String storage;
    
}

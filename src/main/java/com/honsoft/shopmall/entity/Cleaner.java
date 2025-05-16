package com.honsoft.shopmall.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="cleaners")
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class Cleaner extends Product {
	@Column(unique = true, nullable = false)
	private String cleanerId;
    private String brand;
    private Double power;
    
}

package com.honsoft.shopmall.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="cleaners")
@Setter
@Getter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Cleaner extends Product {
	@Column(unique = true, nullable = false)
	private String cleanerId;
    private String brand;
    private Integer powerRating;
    
}

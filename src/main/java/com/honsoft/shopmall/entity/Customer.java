package com.honsoft.shopmall.entity;

import jakarta.persistence.CascadeType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.OneToOne;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data			// Getter Setter
//@Builder		// DTO -> Entity화
//@AllArgsConstructor	// 모든 컬럼 생성자 생성
//@NoArgsConstructor	// 기본 생성자
public class Customer {	
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String customerId;
	
	private String name;              //고객명
	    
	private String phone;             //고객전화번호
	
	
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id")
	private Address address;
	
	
}

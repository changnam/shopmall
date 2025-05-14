package com.honsoft.shopmall.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data			// Getter Setter
//@Builder		// DTO -> Entity화
@AllArgsConstructor	// 모든 컬럼 생성자 생성
@NoArgsConstructor	// 기본 생성자
@Table(name = "customers")
public class Customer {	
	
	@Id 
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String customerId;
	
	@Column(nullable = false)
	private String name;              //고객명
	    
	private String phone;             //고객전화번호
	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "customer", orphanRemoval = true)
	@JsonManagedReference //serialization 할때 Customer -> Address -> Customer -> 무한반복 방지
//	@Builder.Default
	private List<Address> addresses;;
	
	
}

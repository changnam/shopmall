package com.honsoft.shopmall.entity;


import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.OneToOne;
import lombok.Data;


@Entity
@Data			// Getter Setter
//@Builder		// DTO -> Entity화
//@AllArgsConstructor	// 모든 컬럼 생성자 생성
//@NoArgsConstructor	// 기본 생성자
public class Shipping{
	
	 @Id 
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	 

	private String name;        //배송고객명	
	
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private String date;          //배송일
	// @CreatedDate
	// private LocalDateTime date;
	 
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
	private Address address;   //배송 주소 객체
	
	/*public Shipping() {
		this.address = new Address();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	*/
	
}

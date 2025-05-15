package com.honsoft.shopmall.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
//@Builder
@Table(name = "addresses")
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"customer"})
public class Address {

	// private static final long serialVersionUID = 613846598817670033L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long addressId;

	private String country; // 국가명

	private String zipCode; // 우편번호

	@Column(nullable = false)
	private String addressName; // 주소

	private String detailName; // 세부주소

	@ManyToOne(fetch = FetchType.LAZY, optional = false) //반드시 address 에는 customer 가 연결되어야 함
	@JoinColumn(name = "customer_id", nullable = false) //foreign key 생성시 not null 로 생성
	@JsonBackReference // bidirectional relationship 에서 serialization 무한루프 방지
	private Customer customer;

}

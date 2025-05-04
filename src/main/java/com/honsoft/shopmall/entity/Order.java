package com.honsoft.shopmall.entity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Table(name = "orders")
@Data
public class Order{	
	
	@Id @GeneratedValue//(strategy = GenerationType.IDENTITY)
	private Long orderId;                   //주문ID		
			
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "customer_id")
	private Customer customer;            //고객 객체	
	
	@OneToOne(cascade = CascadeType.ALL)	
	@JoinColumn(name = "shipping_id")
	private Shipping shipping;             //배송지 객체	
	
	
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "order_order_id")
	private Map<String,OrderItem> orderItems =new HashMap<>();
	
	private BigDecimal grandTotal; //주문 총 금액	
	
	

}

package com.honsoft.shopmall.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import lombok.Data;

@Data 
@Entity
public class OrderItem{
	
	@Id
    @GeneratedValue
    //@Column(name = "order_item_id")
    private Long id;	

	
//	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
 //   @JoinColumn(name = "item_item_id")
 //   private Item item;

	private String bookId;
	
	//@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "order_id")
    //private Order order;

   	private int quantity; // 주문 수량
	
	private BigDecimal  totalPrice; // 주문 가격
}

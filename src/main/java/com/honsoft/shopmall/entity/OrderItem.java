package com.honsoft.shopmall.entity;

import java.math.BigDecimal;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data 
@Entity
@Table(name = "order_items")
@NoArgsConstructor
@ToString(callSuper = true)
public class OrderItem extends BaseEntity{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "order_item_id")
    private Long id;	

	
//	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
 //   @JoinColumn(name = "item_item_id")
 //   private Item item;

	private String bookId;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "product_id")
	private Product product;
	
	//@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "order_id")
    //private Order order;

   	private int quantity; // 주문 수량
	
	private BigDecimal  totalPrice; // 주문 가격
}

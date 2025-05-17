package com.honsoft.shopmall.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "computer_details")
@NoArgsConstructor
@AllArgsConstructor
public class ComputerDetail extends ProductDetail {
    private String graphicsCard;
}

package com.honsoft.shopmall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CleanerDetailDto extends ProductDetailDto {
    private String filterType;
}

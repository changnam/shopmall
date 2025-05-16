package com.honsoft.shopmall.dto;

import java.time.Instant;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEntityDto {
	 
	    protected String createdBy;

	   
	    protected LocalDateTime createdDate;

	    
	    protected String lastModifiedBy;

	   
	    protected LocalDateTime lastModifiedDate;
	    
	   
	    protected Instant createdAt;

	   
	    protected Instant updatedAt;
}

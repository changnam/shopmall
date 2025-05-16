package com.honsoft.shopmall.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "exception_messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionMessage {

    @Id
    private String code; // e.g., "REVIEW_NOT_FOUND"

    private String message;
    private int httpStatus;
}

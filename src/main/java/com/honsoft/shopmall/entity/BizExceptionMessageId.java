package com.honsoft.shopmall.entity;

import java.io.Serializable;
import java.util.Locale;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BizExceptionMessageId implements Serializable {
    private String code;
    private String locale;
}


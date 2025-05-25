package com.honsoft.shopmall.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
    private Long studentId;
    private String name;
    private List<Long> courseIds; // flat list of course IDs
}

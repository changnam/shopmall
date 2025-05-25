package com.honsoft.shopmall.restcontroller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.honsoft.shopmall.request.StudentDto;
import com.honsoft.shopmall.response.ResponseHandler;
import com.honsoft.shopmall.service.StudentService;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {
	private final StudentService studentService;
	
	public StudentController(StudentService studentService) {
		this.studentService =studentService;
	}
	
	@PostMapping
	public ResponseEntity<Object> createStudent(@RequestBody StudentDto studentDto){
		StudentDto saved = studentService.createStudent(studentDto);
		return ResponseHandler.responseBuilder("save ok", HttpStatus.OK, saved);
	}

}

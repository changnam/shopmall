package com.honsoft.shopmall.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honsoft.shopmall.entity.Student;
import com.honsoft.shopmall.mapper.StudentMapper;
import com.honsoft.shopmall.repository.StudentRepository;
import com.honsoft.shopmall.request.StudentDto;

@Service
public class StudentService {
	private final StudentRepository studentRepository;
	private final StudentMapper studentMapper;
	
	public StudentService(StudentRepository studentRepository,StudentMapper studentMapper) {
		this.studentRepository = studentRepository;
		this.studentMapper = studentMapper;
	}
	
	@Transactional
	public StudentDto createStudent(StudentDto studentDto) {
		Student student = studentMapper.toEntity(studentDto);
		Student saved = studentRepository.save(student);
		StudentDto savedDto = studentMapper.toDto(student);
		return savedDto;
	}
}

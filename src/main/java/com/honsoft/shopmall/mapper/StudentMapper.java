package com.honsoft.shopmall.mapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import com.honsoft.shopmall.entity.Course;
import com.honsoft.shopmall.entity.CourseRegistration;
import com.honsoft.shopmall.entity.CourseRegistrationId;
import com.honsoft.shopmall.entity.Student;
import com.honsoft.shopmall.repository.CourseRepository;
import com.honsoft.shopmall.request.StudentDto;

import jakarta.persistence.EntityNotFoundException;

@Mapper(componentModel = "spring")
public abstract class StudentMapper {

	@Autowired
	protected CourseRepository courseRepository;

	@Mapping(target = "registrations", ignore = true) // we set it manually
	public abstract Student toEntity(StudentDto dto);
//
	@AfterMapping
	public void mapRegistrations(StudentDto dto, @MappingTarget Student student) {
		if (dto.getCourseIds() != null) {
			List<CourseRegistration> registrations = new ArrayList<>();
			for (Long courseId : dto.getCourseIds()) {
				Course course = courseRepository.findById(courseId)
						.orElseThrow(() -> new EntityNotFoundException("Course not found: " + courseId));

				CourseRegistration reg = new CourseRegistration();
				reg.setId(new CourseRegistrationId(student.getStudentId(), courseId));
				reg.setStudent(student);
				reg.setCourse(course);
				reg.setRegisteredAt(LocalDateTime.now());

				registrations.add(reg);
			}
			student.setRegistrations(registrations);
		}
	}

	@Mapping(target = "courseIds", ignore = true)
	public abstract StudentDto toDto(Student student);
//
	@AfterMapping
	public void mapCourseIds(Student student, @MappingTarget StudentDto studentDto) {
		if (student.getRegistrations() == null) {
			studentDto.setCourseIds(List.of());
		}

		List<Long> courseIds = student.getRegistrations().stream().map(CourseRegistration::getCourse)
				.map(Course::getCourseId).collect(Collectors.toList());
		studentDto.setCourseIds(courseIds);
	}

}

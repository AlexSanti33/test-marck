package com.imsoftware.students.service;

import java.util.Collection;

import com.imsoftware.students.domain.StudentDTO;
import com.imsoftware.students.entity.Student;

public interface IStudentService {
	Collection<StudentDTO> findAll();

	Collection<StudentDTO> findAllAndShowIfHaveAPopularSubject();

	Student save(Student studentDTO);

	Student update(Student studentDTO, Integer id);

}

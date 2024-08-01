package com.imsoftware.students.api;

import java.util.Collection;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imsoftware.students.domain.StudentDTO;
import com.imsoftware.students.entity.Student;
import com.imsoftware.students.service.IStudentService;

@RestController
@RequestMapping(path = "prueba")
public class StudentController {

	private final IStudentService studentService;

	public StudentController(IStudentService studentService) {
		super();
		this.studentService = studentService;
	}

	@GetMapping("/students")
	ResponseEntity<Collection<StudentDTO>> all() {
		return ResponseEntity.ok(studentService.findAll());
	}
	
	@GetMapping("/popular-sugject")
	Collection<StudentDTO> allWithPopularSubject() {
		return studentService.findAllAndShowIfHaveAPopularSubject();
	}

	@PostMapping()
	Student allWithPopularSu(@RequestBody Student studentDTO) {
		return studentService.save(studentDTO);
	}

	@PutMapping("/{id}")
	Student allWithPopularSu(@RequestBody Student studentDTO , @PathVariable Integer id) {
		return studentService.update(studentDTO, id);
	}
	
	
}

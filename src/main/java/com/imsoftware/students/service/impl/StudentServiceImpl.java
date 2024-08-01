package com.imsoftware.students.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.imsoftware.students.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.imsoftware.students.domain.StudentDTO;
import com.imsoftware.students.entity.Student;
import com.imsoftware.students.entity.Subject;
import com.imsoftware.students.service.IStudentService;

@Service
public class StudentServiceImpl implements IStudentService {

	private final StudentRepository studentRepository;

	public StudentServiceImpl(StudentRepository studentRepository) {
		super();
		this.studentRepository = studentRepository;
	}

	@Override
	public Collection<StudentDTO> findAll() {
		return studentRepository.findAll()
				.stream().map(new Function<Student, StudentDTO>() {
			
			@Override
			public StudentDTO apply(Student student) {
				List<String> programmingLanguagesKnowAbout = student.getSubjects().stream()
						.map(pl -> new String(pl.getName())).collect(Collectors.toList());
				
				return new StudentDTO(student.getName(), programmingLanguagesKnowAbout);
			}

		}).collect(Collectors.toList());
		
	}

	@Override
	public Collection<StudentDTO> findAllAndShowIfHaveAPopularSubject() {
		// TODO Obtener la lista de todos los estudiantes 
		//e indicar la materia más concurrida existentes en la BD e
		// indicar si el estudiante cursa o no la materia más concurrida registrado en la BD.
		
		//Se obtiene toda la lista de estudiantes
		Collection<Student> sutdents =studentRepository.findAll();		
		
		/*
		 * Se obtiene la materia mas concurrida
		 * */		
		String popularSubject =	sutdents.stream().map(student -> student.getSubjects())
				//Se recorreen las materias que tiene el asignadas cada estudiante usando flatmap para que se tome en cuenta solo la collection interna
				.flatMap(subjects-> subjects.stream())
				//Se agrupan en un mapa Map<String,Long> asignando el nombre como key y el numero de veces que se repite como value 
				.collect(Collectors.groupingBy(x -> x.getName(),Collectors.counting()))
				//Se obtiene el valor maximo recorriendo el mapa creado y comparando el valor de veces que se repite cada materia y al final se obtiene el nombre de la materia
				.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();

		//se ocupa la interface stream y al metodo map se la pasa una funcion anonimia que recibe el objeto que vamos a recorrer y el objeto en el que vamos a transformar esa lista
		return sutdents.stream().map(new Function<Student, StudentDTO>() {
			
			@Override //Se sobreescribe el metodo apply y se asigna un parametro con el tipo y la instancia que se vamos a manipular
			public StudentDTO apply(Student student) {
			
				//Se valida si las asignaturas de cada esstudent hacen match con la asignatura mas popular
		        boolean contains = student.getSubjects().stream().anyMatch(s-> s.getName().equalsIgnoreCase(popularSubject));
		    	/*
				 * Se contruye el studentDTO usando la sobrecarga de constructutores que recibe el el nombre y si toma la asignatura mas popular
				 * */
				return new StudentDTO(student.getName(),contains);
			}
			//Se contruye la lista con el objeto student Creado
		}).collect(Collectors.toList());
	}

	@Override
	public Student save(Student studentDTO) {
		// TODO Auto-generated method stub
		return studentRepository.save(studentDTO);
	}

	@Override
	public Student update(Student studentDTO, Integer id) {
		Student studentDb = studentRepository.findById(id).orElseThrow(()-> new NoSuchElementException("No found id"));
		studentDb.setId(studentDTO.getId());
		
		return studentRepository.save(studentDb);
	}
	
	
	
}

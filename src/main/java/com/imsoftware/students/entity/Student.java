package com.imsoftware.students.entity;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Student {

	@Id
	private Integer id;
	private String name;
	@ManyToMany
	@JoinTable(name = "subject_student",
	joinColumns = {@JoinColumn(name = "student_id")},
	inverseJoinColumns = {@JoinColumn(name = "subject_id") })
	private Collection<Subject> subjects;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(Collection<Subject> subjects) {
		this.subjects = subjects;
	}
}

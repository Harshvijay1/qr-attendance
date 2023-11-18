package com.harsh.qrattendance.pojo;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="Subject")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Subject {
	
	@Id
	private String subjectCode;
	private String subjectName;
	
	//@OneToOne(mappedBy = "subject")
	//private Teacher teacher;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "listOfSubjects")
	private Set<Student> listOfStudents = new HashSet<>();
	
//	public Teacher getTeacher() {
	//	return teacher;
	//}
		
	//public void setTeacher(Teacher teacher) {
	//	this.teacher = teacher;
	//}
	public String getSubjectCode() {
		return subjectCode;
	}
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
}
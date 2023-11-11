package com.harsh.qrattendance.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.harsh.qrattendance.pojo.Student;
import com.harsh.qrattendance.pojo.Teacher;
import com.harsh.qrattendance.repo.StudentRepo;

@RestController
@RequestMapping("/api")
public class StudentController {

	@Autowired
	private StudentRepo studentrepo;

	@GetMapping("/students")
	public ResponseEntity<List<Student>> GetAllStudent() {
		try {
			List<Student> StudentList = new ArrayList<>();
			studentrepo.findAll().forEach(StudentList::add);

			if (StudentList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(StudentList, HttpStatus.OK);

		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/students/{id}")
	public ResponseEntity<Student> GetStudentByEnrollment(@PathVariable String enrollment) {
		Optional<Student> StudentData = studentrepo.findById(enrollment);

		if (StudentData.isPresent()) {
			return new ResponseEntity<>(StudentData.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

	@PostMapping("/students")
	public ResponseEntity<Student> AddStudent(@RequestBody Student student) {

		Student studentObj = studentrepo.save(student);
		return new ResponseEntity<>(studentObj, HttpStatus.OK);

	}

	@PostMapping("/students/{enrollment}")
	public ResponseEntity<Student> UpdateStudentByEnrollment(@PathVariable String enrollment,
			@RequestBody Student NewStudentData) {
		Optional<Student> OldStudentData = studentrepo.findById(enrollment);

		if (OldStudentData.isPresent()) {
			Student UpdatedStudentData = OldStudentData.get();
			UpdatedStudentData.setFirstName(NewStudentData.getFirstName());
			UpdatedStudentData.setLastName(NewStudentData.getLastName());
			UpdatedStudentData.setEmail(NewStudentData.getEmail());
			UpdatedStudentData.setListOfSubjects(NewStudentData.getListOfSubjects());

			Student studentObj = studentrepo.save(UpdatedStudentData);
			return new ResponseEntity<>(studentObj, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}
	
	@DeleteMapping("students/{enrollment}")
	public ResponseEntity<HttpStatus> DeleteStudentByEnrollemnt(@PathVariable String enrollment) {
		studentrepo.deleteById(enrollment);
		return new ResponseEntity<>(HttpStatus.OK);

	}

}

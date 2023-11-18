package com.harsh.qrattendance.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harsh.qrattendance.pojo.Student;
import com.harsh.qrattendance.pojo.Subject;
import com.harsh.qrattendance.repo.StudentRepo;
import com.harsh.qrattendance.repo.SubjectRepo;

@RestController
@RequestMapping("/api")
public class StudentController {

	@Autowired
	private StudentRepo studentRepo;

	@Autowired
	private SubjectRepo subjectRepo;

	@GetMapping("/students")
	public ResponseEntity<List<Student>> GetAllStudent() {
		try {
			List<Student> StudentList = new ArrayList<>();
			studentRepo.findAll().forEach(StudentList::add);

			if (StudentList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(StudentList, HttpStatus.OK);

		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/students/{id}")
	public ResponseEntity<Student> GetStudentById(@PathVariable String id) {
		Optional<Student> StudentData = studentRepo.findById(id);

		if (StudentData.isPresent()) {
			return new ResponseEntity<>(StudentData.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

	@PostMapping("/students")
	public ResponseEntity<Student> AddStudent(@RequestBody Student student) {

		Student studentObj = studentRepo.save(student);
		return new ResponseEntity<>(studentObj, HttpStatus.OK);

	}

	@PostMapping("/students/{id}")
	public ResponseEntity<Student> UpdateStudentById(@PathVariable String id,
			@RequestBody Student NewStudentData) {
		Optional<Student> OldStudentData = studentRepo.findById(id);

		if (OldStudentData.isPresent()) {
			Student UpdatedStudentData = OldStudentData.get();
			UpdatedStudentData.setFirstName(NewStudentData.getFirstName());
			UpdatedStudentData.setLastName(NewStudentData.getLastName());
			UpdatedStudentData.setEmail(NewStudentData.getEmail());
			// UpdatedStudentData.setListOfSubjects(NewStudentData.getListOfSubjects());

			Student studentObj = studentRepo.save(UpdatedStudentData);
			return new ResponseEntity<>(studentObj, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

	@DeleteMapping("students/{id}")
	public ResponseEntity<HttpStatus> DeleteStudentById(@PathVariable String Id) {
		studentRepo.deleteById(Id);
		return new ResponseEntity<>(HttpStatus.OK);

	}
	
	@PostMapping("students/{id}/subjects/{subjectCode}")
	public ResponseEntity<Student> assignedSubjectToStudent(@PathVariable String id, @PathVariable String subjectCode) {
		Set<Subject> SubjectSet = null;
		Student student = studentRepo.findById(id).get();
		Subject subject = subjectRepo.findById(id).get();
		SubjectSet = student.getListOfSubjects();
		SubjectSet.add(subject);
		student.setListOfSubjects(SubjectSet);
		return new ResponseEntity<>(student,HttpStatus.OK);
	}
}

package com.harsh.qrattendance.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import com.harsh.qrattendance.pojo.Teacher;
import com.harsh.qrattendance.repo.TeacherRepo;

@RestController
@RequestMapping("/api")
public class TeacherController {
	@Autowired
	private TeacherRepo teacherRepo;

	@GetMapping("/teachers")
	public ResponseEntity<List<Teacher>> getAllTeacher() {
		try {
			List<Teacher> teacherList = new ArrayList<>();
			teacherRepo.findAll().forEach(teacherList::add);

			if (teacherList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(teacherList, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/teachers/{id}")
	public ResponseEntity<Teacher> getTeacherByid(@PathVariable Long id) {
		Optional<Teacher> TeacherData = teacherRepo.findById(id);

		if (TeacherData.isPresent()) {
			return new ResponseEntity<>(TeacherData.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

	@PostMapping("/teachers")
	public ResponseEntity<Teacher> AddTeacher(@RequestBody Teacher teacher) {

		Teacher teacherObj = teacherRepo.save(teacher);
		return new ResponseEntity<>(teacherObj, HttpStatus.OK);

	}

	@PostMapping("/teachers/{id}")
	public ResponseEntity<Teacher> UpdateTeacherById(@PathVariable Long id, @RequestBody Teacher NewTeacherData) {
		Optional<Teacher> OldTeacherData = teacherRepo.findById(id);

		if (OldTeacherData.isPresent()) {
			Teacher updatedTeacherData = OldTeacherData.get();
			updatedTeacherData.setFirstName(NewTeacherData.getFirstName());
			updatedTeacherData.setLastName(NewTeacherData.getLastName());
			updatedTeacherData.setSubject(NewTeacherData.getSubject());
			
			Teacher teacherObj = teacherRepo.save(updatedTeacherData);
			return new ResponseEntity<>(teacherObj, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

	@DeleteMapping("/teachers/{id}")
	public ResponseEntity<HttpStatus> DeleteTeacherById(@PathVariable Long id) {
		teacherRepo.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);

	}

}

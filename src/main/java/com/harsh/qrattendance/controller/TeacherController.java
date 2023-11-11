package com.harsh.qrattendance.controller;

import java.util.*;

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
	private TeacherRepo teacherrepo;

	@GetMapping("/teachers")
	public ResponseEntity<List<Teacher>> GetAllTeacher() {
		try {
			List<Teacher> TeacherList = new ArrayList<>();
			teacherrepo.findAll().forEach(TeacherList::add);

			if (TeacherList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(TeacherList, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/teachers/{id}")
	public ResponseEntity<Teacher> GetTeacherByid(@PathVariable Long id) {
		Optional<Teacher> TeacherData = teacherrepo.findById(id);

		if (TeacherData.isPresent()) {
			return new ResponseEntity<>(TeacherData.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

	@PostMapping("/teachers")
	public ResponseEntity<Teacher> AddTeacher(@RequestBody Teacher teacher) {

		Teacher teacherObj = teacherrepo.save(teacher);
		return new ResponseEntity<>(teacherObj, HttpStatus.OK);

	}

	@PostMapping("/teachers/{id}")
	public ResponseEntity<Teacher> UpdateTeacherById(@PathVariable Long id, @RequestBody Teacher NewTeacherData) {
		Optional<Teacher> OldTeacherData = teacherrepo.findById(id);

		if (OldTeacherData.isPresent()) {
			Teacher UpdatedTeacherData = OldTeacherData.get();
			UpdatedTeacherData.setFirstName(NewTeacherData.getFirstName());
			UpdatedTeacherData.setLastName(NewTeacherData.getLastName());
			UpdatedTeacherData.setSubject(NewTeacherData.getSubject());

			Teacher teacherObj = teacherrepo.save(UpdatedTeacherData);
			return new ResponseEntity<>(teacherObj, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

	@DeleteMapping("/teachers/{id}")
	public ResponseEntity<HttpStatus> DeleteTeacherById(@PathVariable Long id) {
		teacherrepo.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);

	}

}

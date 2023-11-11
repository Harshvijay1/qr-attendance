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

import com.harsh.qrattendance.pojo.Lecture;
import com.harsh.qrattendance.repo.LectureRepo;

@RestController
@RequestMapping("/api")
public class LectureController {

	@Autowired
	private LectureRepo lecturerepo;

	@PostMapping("/lecture")
	public ResponseEntity<Lecture> AddLecture(@RequestBody Lecture lecture) {

		Lecture lectureObj = lecturerepo.save(lecture);
		return new ResponseEntity<>(lectureObj, HttpStatus.OK);

	}

	@PostMapping("/lecture/{lectureid}")
	public ResponseEntity<Lecture> UpdateStudentByEnrollment(@PathVariable String lectureid,
			@RequestBody Lecture NewLectureData) {
		Optional<Lecture> OldLectureData = lecturerepo.findById(lectureid);

		if (OldLectureData.isPresent()) {
			Lecture UpdatedLectureData = OldLectureData.get();
			UpdatedLectureData.setSubjectCode(NewLectureData.getSubjectCode());
			UpdatedLectureData.setEmployeeId(NewLectureData.getEmployeeId());

			Lecture lectureObj = lecturerepo.save(UpdatedLectureData);
			return new ResponseEntity<>(lectureObj, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

	@DeleteMapping("lecture/{lectureid}")
	public ResponseEntity<HttpStatus> DeleteLectureByEnrollemnt(@PathVariable String lectureid) {
		lecturerepo.deleteById(lectureid);
		return new ResponseEntity<>(HttpStatus.OK);

	}

	@GetMapping("/lecture/{lectureid}")
	public ResponseEntity<Lecture> GetStudentByEnrollment(@PathVariable String lectureid) {
		Optional<Lecture> LectureData = lecturerepo.findById(lectureid);

		if (LectureData.isPresent()) {
			return new ResponseEntity<>(LectureData.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

	@GetMapping("/lecture")
	public ResponseEntity<List<Lecture>> GetAllLecture() {
		try {
			List<Lecture> LectureList = new ArrayList<>();
			lecturerepo.findAll().forEach(LectureList::add);

			if (LectureList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(LectureList, HttpStatus.OK);

		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}

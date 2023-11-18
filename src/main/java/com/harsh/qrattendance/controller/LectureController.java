package com.harsh.qrattendance.controller;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.WriterException;
import com.harsh.qrattendance.pojo.Lecture;
import com.harsh.qrattendance.pojo.Student;
import com.harsh.qrattendance.repo.LectureRepo;
import com.harsh.qrattendance.repo.StudentRepo;

@RestController
@RequestMapping("/api")
public class LectureController {

	@Autowired
	private LectureRepo lectureRepo;

	@Autowired
	private StudentRepo studentRepo;

	@PostMapping("/lecture")
	public ResponseEntity<Lecture> AddLecture(@RequestBody Lecture lecture) {
		Lecture lectureObj = lectureRepo.save(lecture);
		return new ResponseEntity<>(lectureObj, HttpStatus.OK);
	}

	@GetMapping(path = "/lecture/qr/{lectureId}",  produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<BufferedImage> getQRCode(@PathVariable String lectureId) {
		Optional<Lecture> lectureData = lectureRepo.findById(lectureId);

		if (lectureData.isPresent()) {
			try {
				return new ResponseEntity<>(Lecture.createQrCode(lectureId), HttpStatus.OK);
			} catch (WriterException e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}
	
	@Bean
	public HttpMessageConverter<BufferedImage> createImageHttpMessageConverter() {
	    return new BufferedImageHttpMessageConverter();
	}

	@PostMapping("lecture/{lectureId}/student/{studentId}")
	public ResponseEntity<Lecture> assignedSubjectToStudent(@PathVariable String lectureId,
			@PathVariable String studentId) {
		Set<Student> StudentSet = null;
		Lecture lecture = lectureRepo.findById(lectureId).get();
		Student student = studentRepo.findById(studentId).get();
		StudentSet = lecture.getListOfStudents();
		StudentSet.add(student);
		lecture.setListOfStudents(StudentSet);
		Lecture lectureObj = lectureRepo.save(lecture);
		return new ResponseEntity<>(lectureObj, HttpStatus.OK);
	}

	@PostMapping("/lecture/{lectureId}")
	public ResponseEntity<Lecture> UpdateStudentByEnrollment(@PathVariable String lectureId,
			@RequestBody Lecture NewLectureData) {
		Optional<Lecture> OldLectureData = lectureRepo.findById(lectureId);

		if (OldLectureData.isPresent()) {
			Lecture UpdatedLectureData = OldLectureData.get();
			UpdatedLectureData.setSubject(NewLectureData.getSubject());
		//	UpdatedLectureData.setId(NewLectureData.getId());

			Lecture lectureObj = lectureRepo.save(UpdatedLectureData);
			return new ResponseEntity<>(lectureObj, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

	@DeleteMapping("lecture/{lectureId}")
	public ResponseEntity<HttpStatus> DeleteLectureByEnrollemnt(@PathVariable String lectureId) {
		lectureRepo.deleteById(lectureId);
		return new ResponseEntity<>(HttpStatus.OK);

	}

	@GetMapping("/lecture/{lectureId}")
	public ResponseEntity<Lecture> GetStudentByEnrollment(@PathVariable String lectureId) {
		Optional<Lecture> LectureData = lectureRepo.findById(lectureId);

		if (LectureData.isPresent()) {
			return new ResponseEntity<>(LectureData.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

	@GetMapping("/lecture")
	public ResponseEntity<List<Lecture>> GetAllLecture() {
		try {
			List<Lecture> LectureList = new ArrayList<>();
			lectureRepo.findAll().forEach(LectureList::add);

			if (LectureList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(LectureList, HttpStatus.OK);

		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}

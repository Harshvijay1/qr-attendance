package com.harsh.qrattendance.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harsh.qrattendance.pojo.Subject;
import com.harsh.qrattendance.repo.SubjectRepo;

@RestController
@RequestMapping("/api")
public class SubjectController {

	@Autowired
	private SubjectRepo subjectrepo;
	
	@GetMapping("/subjects")
	public ResponseEntity<List<Subject>> GetAllSubject() {
		try {
			List<Subject> SubjectList = new ArrayList<>();
			subjectrepo.findAll().forEach(SubjectList::add);

			if (SubjectList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(SubjectList, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

//	@GetMapping("/subjects/{subjectCode}")
//	public ResponseEntity<Subject> GetSubjectByid(@PathVariable String subjectCode) {
//		Optional<Subject> SubjectData = subjectrepo.findById(subjectCode);
//
//		if (SubjectData.isPresent()) {
//			return new ResponseEntity<>(SubjectData.get(), HttpStatus.OK);
//		}
//		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//
//	}
//
//	@PostMapping("/subjects")
//	public ResponseEntity<Subject> AddSubject(@RequestBody Subject subject) {
//
//		Subject subjectObj = subjectrepo.save(subject);
//		return new ResponseEntity<>(subjectObj, HttpStatus.OK);
//
//	}
//
//	@PostMapping("/subjects/{subjectCode}")
//	public ResponseEntity<Subject> UpdateSubjectById(@PathVariable String subjectCode, @RequestBody Subject NewSubjectData) {
//		Optional<Subject> OldSubjectData = subjectrepo.findById(subjectCode);
//
//		if (OldSubjectData.isPresent()) {
//			Subject UpdatedSubjectData = OldSubjectData.get();
//			UpdatedSubjectData.setSubjectCode(NewSubjectData.getSubjectCode());
//			UpdatedSubjectData.setSubjectName(NewSubjectData.getSubjectName());
//			
//			
//			Subject subjectObj = subjectrepo.save(UpdatedSubjectData);
//			return new ResponseEntity<>(subjectObj, HttpStatus.OK);
//
//		}
//
//		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//
//	}
//
//	@DeleteMapping("/subjects/{subjectCode}")
//	public ResponseEntity<HttpStatus> DeleteSubjectrById(@PathVariable String subjectCode) {
//		subjectrepo.deleteById(subjectCode);
//		return new ResponseEntity<>(HttpStatus.OK);
//
//	}

}

package com.harsh.qrattendance.pojo;

import java.awt.image.BufferedImage;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Lecture")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Lecture {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long lectureId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "subject_id")
	private Subject subject;

	//@ManyToOne(cascade = CascadeType.ALL)
	//@JoinColumn(name = "teacher_id")
	//private Teacher teacher;
	// private String password;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@CreationTimestamp
	private Instant createdOn;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "list_of_students", nullable = true)
	private Set<Student> listOfStudents = new HashSet<>();

	// Getters and Setters

	public Long getLectureId() {
		return lectureId;
	}

	public void setLectureId(Long lectureId) {
		this.lectureId = lectureId;
	}

	public Set<Student> getListOfStudents() {
		return listOfStudents;
	}

	public void setListOfStudents(Set<Student> listOfStudents) {
		this.listOfStudents = listOfStudents;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	//public Teacher getId() {
	//	return teacher;
	//}

	public Instant getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Instant createdOn) {
		this.createdOn = createdOn;
	}

	//public void setId(Teacher id) {
	//	this.teacher = id;
	//}

	public static BufferedImage createQrCode(String lectureid) throws WriterException {
		QRCodeWriter barcodeWriter = new QRCodeWriter();
		BitMatrix bitMatrix = barcodeWriter.encode(lectureid, BarcodeFormat.QR_CODE, 200, 200);

		return MatrixToImageWriter.toBufferedImage(bitMatrix);
	}

}

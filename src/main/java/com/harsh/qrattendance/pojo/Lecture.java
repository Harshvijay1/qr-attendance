package com.harsh.qrattendance.pojo;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

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
import jakarta.persistence.Lob;
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
	private String lectureid;

	@ManyToOne
	@JoinColumn(name = "subjectCode")
	private Subject subject;

	@ManyToOne
	@JoinColumn(name = "employee_id")
	private Teacher employeeId;
	// private String password;

	@CreationTimestamp
	private Instant createdOn;

	@Lob
	private byte[] qrCode;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "listof_students")
	private List<Student> listOfStudentattended = new ArrayList<>();

	// Getters and Setters

	public List<Student> getListOfStudentattended() {
		return listOfStudentattended;
	}

	public void setListOfStudentattended(List<Student> listOfStudentattended) {
		this.listOfStudentattended = listOfStudentattended;
	}

	public Subject getSubjectCode() {
		return subject;
	}

	public void setSubjectCode(Subject subjectCode) {
		this.subject = subjectCode;
	}

	public Teacher getEmployeeId() {
		return employeeId;
	}

	public Instant getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Instant createdOn) {
		this.createdOn = createdOn;
	}

	public void setEmployeeId(Teacher employeeId) {
		this.employeeId = employeeId;
	}

	public byte[] getQrCode() {
		return qrCode;
	}

	public void setQrCode(byte[] qrCode) {
		this.qrCode = qrCode;
	}

	public byte[] createQrCode(String lectureid) throws WriterException {
		QRCodeWriter barcodeWriter = new QRCodeWriter();
		BitMatrix bitMatrix = barcodeWriter.encode(lectureid, BarcodeFormat.QR_CODE, 200, 200);

		BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
		return ((DataBufferByte) bufferedImage.getData().getDataBuffer()).getData();
	}

}

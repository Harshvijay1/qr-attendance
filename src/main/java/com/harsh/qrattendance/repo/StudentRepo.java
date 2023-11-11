package com.harsh.qrattendance.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harsh.qrattendance.pojo.Student;

@Repository
public interface StudentRepo extends JpaRepository<Student,String> {

}

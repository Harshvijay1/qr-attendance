package com.harsh.qrattendance.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.harsh.qrattendance.pojo.Lecture;

@Repository
public interface LectureRepo extends JpaRepository<Lecture,String> {

}

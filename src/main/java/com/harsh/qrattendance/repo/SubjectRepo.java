package com.harsh.qrattendance.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harsh.qrattendance.pojo.Subject;

@Repository
public interface SubjectRepo extends JpaRepository<Subject,String> {

}

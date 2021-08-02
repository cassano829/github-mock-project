package com.mockproject.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mockproject.model.Subject;


@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer>{
	
	List<Subject> findSubjectByUser_idUser(int id);
}

package com.mockproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mockproject.model.Class;
import com.mockproject.model.Subject;

@Repository
public interface ClassRepository extends JpaRepository<Class, Integer> {
	List<Class> findClassByUser_idUserAndStatus(int id,boolean status);
	
	List<Class> findClassByUser_idUserAndSubject_idSubjectAndStatus(int idUser,int idSubject,boolean status);
	
	Class findClassByIdClass(int id);
}

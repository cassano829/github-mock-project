package com.mockproject.service;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mockproject.model.Subject;
import com.mockproject.repository.ClassRepository;
import com.mockproject.repository.SubjectRepository;
import com.mockproject.model.Class;


@Service
public class ClassService {
	@Autowired
	private ClassRepository classRepository;
	
	public List<Class> findClassByIdUser(int id,boolean status){
		return classRepository.findClassByUser_idUserAndStatus(id,status);
	}
	
	public List<Class> findClassByUser_idUserAndSubject_idSubjectAndStatus(int idUser,int idSubject,boolean status){
		return classRepository.findClassByUser_idUserAndSubject_idSubjectAndStatus(idUser,idSubject,status);
	}
	
	public Class findClassById(int id){
		return classRepository.findClassByIdClass(id);
	}
}



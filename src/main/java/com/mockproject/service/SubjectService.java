package com.mockproject.service;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mockproject.model.Subject;
import com.mockproject.repository.SubjectRepository;


@Service
public class SubjectService {
	@Autowired
	private SubjectRepository subjectRepository;
	
	public List<Subject> findSubjectByIdUser(int id){
		return subjectRepository.findSubjectByUser_idUser(id);
	}
}

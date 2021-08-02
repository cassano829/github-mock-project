package com.mockproject.service;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mockproject.model.Subject;
import com.mockproject.repository.ClassRepository;
import com.mockproject.repository.QuizOfClassRepository;
import com.mockproject.repository.SubjectRepository;
import com.mockproject.model.Class;
import com.mockproject.model.QuizOfClass;


@Service
public class QuizOfClassService {
	@Autowired
	private QuizOfClassRepository quizOfClassRepository;
	
	public List<QuizOfClass> findQuizOfClassByIdClass(int id){
		return quizOfClassRepository.findQuizOfClassByClas_idClass(id);
	}
}

package com.mockproject.service;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mockproject.model.Subject;
import com.mockproject.repository.QuizOfClassRepository;
import com.mockproject.repository.QuizRepository;
import com.mockproject.model.Class;
import com.mockproject.model.Quiz;
import com.mockproject.model.QuizOfClass;


@Service
public class QuizService {
	@Autowired
	private QuizRepository quizRepository;
	
	public Quiz findQuizByIdQuiz(int id){
		return quizRepository.findQuizByIdQuiz(id);
	}
}

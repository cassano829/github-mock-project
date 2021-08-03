package com.mockproject.service;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mockproject.repository.QuestionRepository;
import com.mockproject.model.Question;


@Service
public class QuestionService {
	@Autowired
	private QuestionRepository questionRepository;
	
	
	public List<Question> findQuestionByidQuestion(int id){
		return questionRepository.findQuestionByIdQuestion(id);
	}
	
	
}

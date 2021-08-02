package com.mockproject.service;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mockproject.model.Subject;
import com.mockproject.repository.ClassRepository;
import com.mockproject.repository.QuestionRepository;
import com.mockproject.repository.SubjectRepository;
import com.mockproject.model.Class;
import com.mockproject.model.Question;


@Service
public class QuestionService {
	@Autowired
	private QuestionRepository questionRepository;
	
	public List<Question> findQuestionByQuiz_idQuizAndStatus(int id,boolean status){
		return questionRepository.findQuestionByQuiz_idQuizAndStatus(id,status);
	}
	
	public List<Question> findQuestionByidQuestion(int id){
		return questionRepository.findQuestionByidQuestion(id);
	}
	
	
}

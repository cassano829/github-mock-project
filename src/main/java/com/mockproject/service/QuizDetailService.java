package com.mockproject.service;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mockproject.model.Subject;
import com.mockproject.repository.QuizDetailRepository;
import com.mockproject.repository.QuizOfClassRepository;
import com.mockproject.repository.QuizOfUserRepository;
import com.mockproject.model.Class;
import com.mockproject.model.QuizDetail;
import com.mockproject.model.QuizOfClass;
import com.mockproject.model.QuizOfUser;


@Service
public class QuizDetailService {
	@Autowired
	private QuizDetailRepository quizDetailRepository;
	
	public List<QuizDetail> findQuizDetailByQuizOfUser_idQuizOfUser(int id){
		return quizDetailRepository.findQuizDetailByQuizOfUser_idQuizOfUser(id);
	}
}

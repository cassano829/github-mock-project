package com.mockproject.service;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mockproject.model.Subject;
import com.mockproject.repository.QuizOfClassRepository;
import com.mockproject.repository.QuizOfUserRepository;
import com.mockproject.model.Class;
import com.mockproject.model.Quiz;
import com.mockproject.model.QuizOfClass;
import com.mockproject.model.QuizOfUser;


@Service
public class QuizOfUserService {
	@Autowired
	private QuizOfUserRepository quizOfUserRepository;
	
	public List<QuizOfUser> findQuizOfUserByIdUserAndIdQuiz(int idUser,int idQuiz){
		return quizOfUserRepository.findQuizOfUserByIdUserAndIdQuiz(idUser,idQuiz);
	}
	
}

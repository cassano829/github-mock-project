package com.mockproject.service;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mockproject.repository.QuizOfUserRepository;
import com.mockproject.model.QuizOfUser;


@Service
public class QuizOfUserService {
	@Autowired
	private QuizOfUserRepository quizOfUserRepository;
	
	public QuizOfUser saveQuizOfUser(QuizOfUser quizOfUser){
		return quizOfUserRepository.save(quizOfUser);
	}
	
	public List<QuizOfUser> findQuizOfUserByUser_idUserAndQuiz_idQuiz(int idUser,int idQuiz){
		return quizOfUserRepository.findQuizOfUserByUser_idUserAndQuiz_idQuiz(idUser,idQuiz);
	}
	
	public List<QuizOfUser> findQuizOfUserByUser_idUser(int idUser){
		return quizOfUserRepository.findQuizOfUserByUser_idUser(idUser);
	}
	
	
	public void updateQuizesOfUserByidQuizOfUser(int totalCorrect,boolean isPass,int idQuizOfUser,float grade){
		 quizOfUserRepository.updateQuizesOfUserByidQuizOfUser(totalCorrect, isPass, idQuizOfUser,grade);
	}
	
	
}

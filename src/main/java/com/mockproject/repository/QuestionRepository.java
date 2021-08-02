package com.mockproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mockproject.model.Class;
import com.mockproject.model.Question;
import com.mockproject.model.Subject;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
	List<Question> findQuestionByQuiz_idQuizAndStatus(int id,boolean status);
	
	List<Question> findQuestionByidQuestion(int id);
}

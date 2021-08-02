package com.mockproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mockproject.model.Quiz;
import com.mockproject.model.QuizOfClass;
import com.mockproject.model.Subject;


@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {
	Quiz findQuizByIdQuiz(int id);
}

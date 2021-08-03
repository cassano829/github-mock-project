package com.mockproject.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mockproject.model.Class;
import com.mockproject.model.Quiz;
import com.mockproject.model.QuizOfUser;
import com.mockproject.model.Subject;

@Repository
public interface QuizOfUserRepository extends JpaRepository<QuizOfUser, Integer> {
	public List<QuizOfUser> findQuizOfUserByIdUserAndIdQuiz(int idUser,int idQuiz);
}

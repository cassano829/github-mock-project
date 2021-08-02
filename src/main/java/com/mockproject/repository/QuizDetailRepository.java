package com.mockproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mockproject.model.Class;
import com.mockproject.model.QuizDetail;
import com.mockproject.model.QuizOfClass;
import com.mockproject.model.Subject;


@Repository
public interface QuizDetailRepository extends JpaRepository<QuizDetail, Integer> {
	List<QuizDetail> findQuizDetailByQuizOfUser_idQuizOfUser(int id);
}

package com.mockproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mockproject.model.Answer;
import com.mockproject.model.Class;
import com.mockproject.model.Question;
import com.mockproject.model.Subject;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {
	List<Answer> findAnswerByQuestion_idQuestion(int id);
}

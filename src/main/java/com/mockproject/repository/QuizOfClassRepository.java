package com.mockproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mockproject.model.QuizOfClass;
import com.mockproject.model.Subject;


@Repository
public interface QuizOfClassRepository extends JpaRepository<QuizOfClass, Integer> {
	List<QuizOfClass> findQuizOfClassByClas_idClass(int id);
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.repository;

import com.mockproject.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 *
 * @author Asus
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer>{
    
    @Query("SELECT q FROM Question q WHERE idQuiz=?1")
    public List<Question> findListQuestionByIdQuiz(int idQuiz) ;
    
}

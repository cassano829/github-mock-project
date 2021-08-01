/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.service;

import com.mockproject.model.Quiz;
import com.mockproject.model.QuizOfClass;
import com.mockproject.repository.QuizOfClassRepository;
import com.mockproject.repository.QuizRepository;
import java.util.List;
import java.util.Set;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author Asus
 */
@Service
public class QuizService {
    
    @Autowired
    QuizRepository quizRepository;
    
    @Autowired
    QuizOfClassRepository quizOfClassRepository;

    public Quiz getQuizByIdQuiz(int idQuiz){
        return quizRepository.getById(idQuiz);
    }
    
    @Transactional
    public void saveQuiz(Quiz quiz){
        quizRepository.saveAndFlush(quiz);
    }
    
    public Page<Quiz> searchQuiz(String search,int idClass,Pageable pageable){
        return quizRepository.findAllByNameQuiz(search,idClass,pageable);
    }
    
    public int getLastIdQuiz(){
        return quizRepository.getIdOfQuiz();
    }
}

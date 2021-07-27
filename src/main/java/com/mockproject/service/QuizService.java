/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.service;

import com.mockproject.model.Quiz;
import com.mockproject.repository.QuizRepository;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Asus
 */
@Service
public class QuizService {
    
    @Autowired
    QuizRepository repository;

    public Quiz getQuizByIdQuiz(int idQuiz){
        return repository.getById(idQuiz);
    }
    
    public List<Quiz> getAllQuizByIdClassAndIdSubject(int idClass,String idSubject){
        return repository.getAllQuizByIdClassAndIdSubject(idClass,idSubject);
    }
}

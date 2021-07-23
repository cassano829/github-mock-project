/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.service;

import com.mockproject.model.QuizDetail;
import com.mockproject.repository.QuizDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Asus
 */
@Service
public class QuizDetailService {
    @Autowired
    QuizDetailRepository repository;
    
    public void insertQuizDetail(QuizDetail quizDetail){
        repository.save(quizDetail);
    }
}

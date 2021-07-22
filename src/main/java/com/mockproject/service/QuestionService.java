/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.service;

import com.mockproject.repository.QuestionRepository;
import com.mockproject.model.Question;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Asus
 */
@Service
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    public void save(Question question) {
        questionRepository.save(question);
    }
    
    public List<Question> findListQuestionByidSubject(String idSubject){
        return questionRepository.findListQuestionBySubject(idSubject);
    }
}

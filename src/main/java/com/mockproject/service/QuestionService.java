/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.service;

import com.mockproject.model.Answer;
import com.mockproject.model.Question;
import com.mockproject.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    
    public List<Question> findListQuestionByIdQuiz(int idQuiz){
        return questionRepository.findListQuestionByIdQuiz(idQuiz);
    }
    
    public int findAnswerByIdCorrect(int questionId){
        Question question=questionRepository.findById(questionId).get();
        for(Answer answer:question.getAnswers()){
            if(answer.isIsCorrect()){
                return answer.getIdAnswer();
            }
        }
        return -1;
    }
    
    
}

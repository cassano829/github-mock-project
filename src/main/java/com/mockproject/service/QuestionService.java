/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.service;

import com.mockproject.model.Answer;
import com.mockproject.repository.QuestionRepository;
import com.mockproject.model.Question;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author Asus
 */
@Service
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    public Question findbyId(int idQuestion){
        return questionRepository. getById(idQuestion);
    }
    public void save(Question question) {
        questionRepository.save(question);
    }
    
    public List<Question> findListQuestionByIdQuiz(int numOfQues,int idQuiz){
        return questionRepository.findListQuestionByIdQuiz(numOfQues,idQuiz);
    }
    
    public Page<Question> getQuestionsByIdQuiz(int idQuiz,Pageable pageable){
        return questionRepository.findAllByIdQuiz(idQuiz,pageable);
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
    
    public int countNumOfQuesOfQuiz(int idQuiz){
        return questionRepository.countNumOfQuesOfQuiz(idQuiz);
    }
    
}

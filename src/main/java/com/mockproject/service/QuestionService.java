<<<<<<< HEAD
package com.mockproject.service;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mockproject.model.Subject;
import com.mockproject.repository.ClassRepository;
import com.mockproject.repository.QuestionRepository;
import com.mockproject.repository.SubjectRepository;
import com.mockproject.model.Class;
import com.mockproject.model.Question;


@Service
public class QuestionService {
	@Autowired
	private QuestionRepository questionRepository;
	
	public List<Question> findQuestionByQuiz_idQuizAndStatus(int id,boolean status){
		return questionRepository.findQuestionByQuiz_idQuizAndStatus(id,status);
	}
	
	public List<Question> findQuestionByidQuestion(int id){
		return questionRepository.findQuestionByidQuestion(id);
	}
	
	
=======
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

    public Question findbyId(int idQuestion) {
        return questionRepository.getById(idQuestion);
    }

    public void save(Question question) {
        questionRepository.save(question);
    }

    public List<Question> findListQuestionByIdQuiz(int limit, int idQuiz) {
        return questionRepository.findListQuestionByIdQuiz(limit, idQuiz);
    }

    public Page<Question> getQuestionsByIdQuiz(int idQuiz, Pageable pageable) {
        return questionRepository.findAllByIdQuiz(idQuiz, pageable);
    }

    public int findAnswerByIdCorrect(int questionId) {
        Question question = questionRepository.findById(questionId).get();
        for (Answer answer : question.getAnswers()) {
            if (answer.isIsCorrect()) {
                return answer.getIdAnswer();
            }
        }
        return -1;
    }

    public int countNumOfQuesOfQuiz(int idQuiz) {
        return questionRepository.countNumOfQuesOfQuiz(idQuiz);
    }

>>>>>>> master
}

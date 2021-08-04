package com.mockproject.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mockproject.repository.QuizOfClassRepository;
import com.mockproject.model.QuizOfClass;

@Service
public class QuizOfClassService {

    @Autowired
    private QuizOfClassRepository quizOfClassRepository;

    public List<QuizOfClass> findQuizOfClassByIdClass(int id) {
        return quizOfClassRepository.findQuizOfClassByIdClass(id);
    }
    
    public QuizOfClass findByIdQuizAndIdClass(int idQuiz, int idClass){
        return quizOfClassRepository.findByIdQuizAndIdClass(idQuiz, idClass);
    }
    
    public List<Integer> findAllByIdQuiz(int idQuiz){
        return quizOfClassRepository.findAllByIdQuiz(idQuiz);
    }
    
    public void save(QuizOfClass qoc){
        quizOfClassRepository.save(qoc);
    }
    
    public void delete(QuizOfClass qoc){
        quizOfClassRepository.delete(qoc);
    }
}


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.service;

import com.mockproject.model.QuizDetail;
import com.mockproject.repository.QuizDetailRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ACER
 */
@Service
public class QuizDetailService {

    @Autowired
    QuizDetailRepository repository;

    public void insertQuizDetail(QuizDetail quizDetail) {
        repository.save(quizDetail);
    }

    public List<QuizDetail> findQuizDetailByQuizOfUser_idQuizOfUser(int id) {
        return repository.findQuizDetailByQuizOfUser_idQuizOfUser(id);
    }

}

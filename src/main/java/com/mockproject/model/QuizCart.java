/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.model;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author Asus
 */
public class QuizCart implements Serializable{
    private int idUser;
    private int idQuiz;
    
    private HashMap<Integer, Integer> quizCart;

    public QuizCart(int user_id,int idQuiz) {
        this.idUser = user_id;
        this.idQuiz=idQuiz;
        this.quizCart = new HashMap<>();
    }

    public int getIdQuiz() {
        return idQuiz;
    }

    public void setIdQuiz(int idQuiz) {
        this.idQuiz = idQuiz;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    

    public HashMap<Integer, Integer> getQuizCart() {
        return quizCart;
    }

    public void setQuizCart(HashMap<Integer, Integer> quizCart) {
        this.quizCart = quizCart;
    }

}

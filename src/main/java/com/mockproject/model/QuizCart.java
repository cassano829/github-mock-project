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
    private String user_id;
    
    private HashMap<Integer, Integer> quizCart;

    public QuizCart(String user_id) {
        this.user_id = user_id;
        this.quizCart = new HashMap<>();
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public HashMap<Integer, Integer> getQuizCart() {
        return quizCart;
    }

    public void setQuizCart(HashMap<Integer, Integer> quizCart) {
        this.quizCart = quizCart;
    }

}

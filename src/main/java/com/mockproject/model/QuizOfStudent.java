/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.model;

import lombok.Data;

import javax.persistence.*;

/**
 *
 * @author Asus
 */
@Entity
@Data
@Table(name = "QuizesOfUser")
public class QuizOfStudent {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idQuizOfUser;
    
    private int idUser;
    
    private boolean isPass;
    
    private int totalCorrect;
    
    private int idQuiz;
    
}

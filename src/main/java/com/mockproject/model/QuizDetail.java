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
@Table(name = "QuizDetails")
public class QuizDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idQuizDetail;
    
    private int idQuizOfUser;
    
    private int idQuestion;
    
    private int userAnswer;
}

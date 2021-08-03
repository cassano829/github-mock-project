/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author ACER
 */
@Entity
@Data
@Table(name = "QuizesOfUser")
public class QuizOfStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idQuizOfUser;

    //Update
    private int idUser;

    private boolean isPass;

    private int totalCorrect;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", shape = JsonFormat.Shape.STRING)
    @Column(name = "submitDate")
    private String submitDate;

    private int idQuiz;
    
    private double grade;

    @OneToMany(mappedBy = "quizOfStudent",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<QuizDetail> quizDetails;
}

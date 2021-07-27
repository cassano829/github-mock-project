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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

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
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="idUser")
    private User user;
    
    private boolean isPass;
    
    private int totalCorrect;
    
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", shape = JsonFormat.Shape.STRING)
    @Column(name = "submitDate")
    private String submitDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="idQuiz")
    private Quiz quiz;
    
    @OneToMany(mappedBy = "quizOfStudent",cascade = CascadeType.ALL)
    private Set<QuizDetail> quizDetails;
    
}

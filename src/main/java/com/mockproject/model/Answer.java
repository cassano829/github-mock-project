/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 *
 * @author Asus
 */
@Entity
@Table(name = "Answers")
public class Answer implements Serializable {

    private int idAnswer;
    private Question question;
    private String content;
    private boolean isCorrect;

    public Answer() {
    }

    public Answer(Question question, String answerContent, boolean isCorrect) {
        this.question = question;
        this.content = answerContent;
        this.isCorrect = isCorrect;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAnswer", unique = true, nullable = false)
    public int getIdAnswer() {
        return idAnswer;
    }

    public void setIdAnswer(int idAnswer) {
        this.idAnswer = idAnswer;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idQuestion", nullable = false)
    public Question getQuestion() {
        return this.question;
    }
    
    public void setQuestion(Question question) {
        this.question = question;
    }
    
    @Column(name = "content",nullable = false,length = 65535)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "isCorrect",nullable = false)
    public boolean isIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }
}

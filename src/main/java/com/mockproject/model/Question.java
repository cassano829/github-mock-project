/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Asus
 */
@Entity
@Table(name = "Questions")
public class Question {

    public Question() {
    }

    private int idQuestion;
    private int idQuiz;
    private String content;
    private boolean status;
    private Set<Answer> answers = new HashSet<Answer>(0);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }



    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public Question(int idQuiz) {
        this.idQuiz = idQuiz;
    }

    public void setContent(String content) {
        this.content = content;
    }


    @Column(name = "status")
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    private String createDate;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @Column(name = "createDate")
    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "question")
    public Set<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
    }

    @Column(name = "idQuiz")
    public int getIdQuiz() {
        return idQuiz;
    }

    public void setIdQuiz(int idQuiz) {
        this.idQuiz = idQuiz;
    }

    public Question(int idQuiz, String questionContent, boolean status, String createDate) {
        this.idQuiz = idQuiz;
        this.content = questionContent;
        this.status = status;
        this.createDate = createDate;
    }

    public Question(int idQuiz, String questionContent, Set<Answer> answer) {
        this.idQuiz = idQuiz;
        this.content = questionContent;
        this.answers=answer;
    }

    
}

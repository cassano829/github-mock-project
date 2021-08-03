package com.mockproject.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;


@Entity
@Data
@Table(name="QuizDetails")
public class QuizDetail {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idQuizDetail;
    
    @Column( unique = true, updatable = false)
    private String userAnswer;

	private Integer idQuestion;
  
    @ManyToOne
	@JoinColumn(name="idQuizOfUser")
	private QuizOfUser quizOfUser;

	public Integer getIdQuizDetail() {
		return idQuizDetail;
	}

	public void setIdQuizDetail(Integer idQuizDetail) {
		this.idQuizDetail = idQuizDetail;
	}

	public String getUserAnswer() {
		return userAnswer;
	}

	public void setUserAnswer(String userAnswer) {
		this.userAnswer = userAnswer;
	}

	public Integer getIdQuestion() {
		return idQuestion;
	}

	public void setIdQuestion(Integer idQuestion) {
		this.idQuestion = idQuestion;
	}

	public QuizOfUser getQuizOfUser() {
		return quizOfUser;
	}

	public void setQuizOfUser(QuizOfUser quizOfUser) {
		this.quizOfUser = quizOfUser;
	}
    
    
    
    
    
}

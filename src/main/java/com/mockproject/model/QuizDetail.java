
package com.mockproject.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
//=======
///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.mockproject.model;
//
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//>>>>>>> master
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//<<<<<<< HEAD
//import javax.persistence.OneToMany;
//import javax.persistence.Table;
//
//import lombok.Data;
//
//
//@Entity
//@Data
//@Table(name="QuizDetails")
//public class QuizDetail {
//	@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer idQuizDetail;
//    
//    @Column( unique = true, updatable = false)
//    private String userAnswer;
//
//    @ManyToOne
//	@JoinColumn(name="idQuestion")
//	private Question question;	
//  
//    @ManyToOne
//	@JoinColumn(name="idQuizOfUser")
//	private QuizOfUser quizOfUser;
//
//    
//    
//	public QuizDetail() {
//		super();
//	}
//
//	public QuizDetail(String userAnswer, Question question, QuizOfUser quizOfUser) {
//		super();
//		this.userAnswer = userAnswer;
//		this.question = question;
//		this.quizOfUser = quizOfUser;
//	}
//
//	public Integer getIdQuizDetail() {
//		return idQuizDetail;
//	}
//
//	public void setIdQuizDetail(Integer idQuizDetail) {
//		this.idQuizDetail = idQuizDetail;
//	}
//
//	public String getUserAnswer() {
//		return userAnswer;
//	}
//
//	public void setUserAnswer(String userAnswer) {
//		this.userAnswer = userAnswer;
//	}
//
//	public Question getQuestion() {
//		return question;
//	}
//
//	public void setQuestion(Question question) {
//		this.question = question;
//	}
//
//	public QuizOfUser getQuizOfUser() {
//		return quizOfUser;
//	}
//
//	public void setQuizOfUser(QuizOfUser quizOfUser) {
//		this.quizOfUser = quizOfUser;
//	}
//    
//    
//    
//=======
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author Asus
 */
@Entity
@Data
@Table(name = "QuizDetails")
public class QuizDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idQuizDetail;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idQuizOfUser")
    private QuizOfUser quizOfStudent;
    
    private int userAnswer;
    
    private int idQuestion;
}

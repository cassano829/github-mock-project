//<<<<<<< HEAD
//package com.mockproject.model;
//
//import java.sql.Date;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.OneToMany;
//import javax.persistence.Table;
//
//import lombok.Data;
//
//
//@Entity
//@Data
//@Table(name="Quizes")
//public class Quiz implements Comparable<Quiz> {
//	@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer idQuiz;
//    
//    @Column(nullable = false, unique = true)
//    private Integer numOfQues;
//    
//    @Column(nullable = false, unique = true, updatable = false)
//    private Date timeLimit;
//    
//    @Column(nullable = false, unique = true, updatable = false)
//    private boolean status;
//    
//    @Column(nullable = false, unique = true, updatable = false)
//    private Date createDate;
//    
//    @Column(nullable = false, unique = true, updatable = false)
//    private String name;
//
//    
//    @ManyToOne
//	@JoinColumn(name="idUser")
//	private User user;
//
//   
//    @OneToMany(mappedBy = "quiz")
//	private List<Report> reports = new ArrayList<>();
//
//    @OneToMany(mappedBy = "quiz")
//	private List<QuizOfClass> quizOfClass = new ArrayList<>();
//    
//    @OneToMany(mappedBy = "quiz")
//	private List<Question> question = new ArrayList<>();
//    
//    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
//	private List<QuizOfUser> quizOfUser = new ArrayList<>();
//   
//
//
//	@Override
//	public int compareTo(Quiz o) {
//		// TODO Auto-generated method stub
//		return this.getIdQuiz() - o.getIdQuiz();
//	}
//	
//	
///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
package com.mockproject.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author ACER
 */
@Entity
@Table(name = "Quizes")
public class Quiz implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idQuiz;

    private int idUser;

    private int numOfQues;

    private int timeLimit;

    private String status;

    private String nameQuiz;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date openDate;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date dueDate;

    private String idSubject;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "quiz",cascade = CascadeType.ALL)
    private Set<Question> questions;
}

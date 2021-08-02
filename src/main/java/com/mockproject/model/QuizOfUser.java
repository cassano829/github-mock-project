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
@Table(name="QuizesOfUser")
public class QuizOfUser {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idQuizOfUser;
    
    @Column(nullable = false, unique = true)
    private boolean isPass;
    
    @Column(nullable = false, unique = true)
    private int totalCorrect;
    
    @Column
    private float grade;
    
    @ManyToOne
	@JoinColumn(name="idUser")
	private User user;	

    
    @ManyToOne
	@JoinColumn(name="idQuiz")
	private Quiz quiz;
    
    @OneToMany(mappedBy = "quizOfUser")
	private List<QuizDetail> quizDetails = new ArrayList<>();

    
    
	public QuizOfUser() {
		
	}

	public QuizOfUser(boolean isPass, int totalCorrect, User user, Quiz quiz) {
		super();
		this.isPass = isPass;
		this.totalCorrect = totalCorrect;
		this.user = user;
		this.quiz = quiz;
	}

	public Integer getIdQuizOfUser() {
		return idQuizOfUser;
	}

	public void setIdQuizOfUser(Integer idQuizOfUser) {
		this.idQuizOfUser = idQuizOfUser;
	}

	public boolean isPass() {
		return isPass;
	}

	public void setPass(boolean isPass) {
		this.isPass = isPass;
	}

	public int getTotalCorrect() {
		return totalCorrect;
	}

	public void setTotalCorrect(int totalCorrect) {
		this.totalCorrect = totalCorrect;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Quiz getQuiz() {
		return quiz;
	}

	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}

	public List<QuizDetail> getQuizDetails() {
		return quizDetails;
	}

	public void setQuizDetails(List<QuizDetail> quizDetails) {
		this.quizDetails = quizDetails;
	}

	public float getGrade() {
		return grade;
	}

	public void setGrade(float grade) {
		this.grade = grade;
	}
    
    
    
}

package com.mockproject.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
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
@Table(name="Quizes")
public class Quiz implements Comparable<Quiz> {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idQuiz;
    
    @Column(nullable = false, unique = true)
    private Integer numOfQues;
    
    @Column(nullable = false, unique = true, updatable = false)
    private Date timeLimit;
    
    @Column(nullable = false, unique = true, updatable = false)
    private boolean status;
    
    @Column(nullable = false, unique = true, updatable = false)
    private Date createDate;
    
    @Column(nullable = false, unique = true, updatable = false)
    private String name;

    
    @ManyToOne
	@JoinColumn(name="idUser")
	private User user;

   
    @OneToMany(mappedBy = "quiz")
	private List<Report> reports = new ArrayList<>();

    @OneToMany(mappedBy = "quiz")
	private List<QuizOfClass> quizOfClass = new ArrayList<>();
    
    @OneToMany(mappedBy = "quiz")
	private List<Question> question = new ArrayList<>();
    
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
	private List<QuizOfUser> quizOfUser = new ArrayList<>();
    
    
    
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Integer getIdQuiz() {
		return idQuiz;
	}


	public void setIdQuiz(Integer idQuiz) {
		this.idQuiz = idQuiz;
	}


	public Integer getNumOfQues() {
		return numOfQues;
	}


	public void setNumOfQues(Integer numOfQues) {
		this.numOfQues = numOfQues;
	}


	public Date getTimeLimit() {
		return timeLimit;
	}


	public void setTimeLimit(Date timeLimit) {
		this.timeLimit = timeLimit;
	}


	public boolean isStatus() {
		return status;
	}


	public void setStatus(boolean status) {
		this.status = status;
	}


	public Date getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	@Override
	public int compareTo(Quiz o) {
		// TODO Auto-generated method stub
		return this.getIdQuiz() - o.getIdQuiz();
	}
	
	
    
}

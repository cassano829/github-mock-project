package com.mockproject.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
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


@Entity
@Data
@Table(name="Quizes")
public class Quiz implements Comparable<Quiz> {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idQuiz;
    
    @Column
    private Integer numOfQues;
    
    @Column
    private Date timeLimit;
    
    @Column
    private boolean status;
    
    @Column
    private Date createDate;
    
    @Column
    private String name;

    
    private Integer idUser;
    
    @OneToMany(mappedBy = "quiz",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Question> question;
    
    
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



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public Integer getIdUser() {
		return idUser;
	}



	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}


	public Set<Question> getQuestion() {
		return question;
	}



	public void setQuestion(Set<Question> question) {
		this.question = question;
	}



	@Override
	public int compareTo(Quiz o) {
		// TODO Auto-generated method stub
		return this.getIdQuiz() - o.getIdQuiz();
	}
	
	
    
}

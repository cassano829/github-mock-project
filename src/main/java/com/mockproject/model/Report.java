package com.mockproject.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;


@Entity
@Data
@Table(name="Reports")
public class Report {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idReport;
    

    @Column(nullable = false, unique = true)
    private float grade;
    
    @ManyToOne
	@JoinColumn(name="idUser")
	private User user;


    @ManyToOne
   	@JoinColumn(name="idQuiz")
   	private Quiz quiz;
    
    @ManyToOne
	@JoinColumn(name="idClass")
	private Class clas;
    
    @ManyToOne
	@JoinColumn(name="idAssignment")
	private Assignment assignment;

	public Integer getIdReport() {
		return idReport;
	}

	public void setIdReport(Integer idReport) {
		this.idReport = idReport;
	}

	public float getGrade() {
		return grade;
	}

	public void setGrade(float grade) {
		this.grade = grade;
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

	public Class getClas() {
		return clas;
	}

	public void setClas(Class clas) {
		this.clas = clas;
	}
}

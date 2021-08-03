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
    
   
	private Integer idUser;


	private Integer idQuiz;
    
	private Integer idClass;
    
	private Integer idAssignment;

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

	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	public Integer getIdQuiz() {
		return idQuiz;
	}

	public void setIdQuiz(Integer idQuiz) {
		this.idQuiz = idQuiz;
	}

	public Integer getIdClass() {
		return idClass;
	}

	public void setIdClass(Integer idClass) {
		this.idClass = idClass;
	}

	public Integer getIdAssignment() {
		return idAssignment;
	}

	public void setIdAssignment(Integer idAssignment) {
		this.idAssignment = idAssignment;
	}

	
}

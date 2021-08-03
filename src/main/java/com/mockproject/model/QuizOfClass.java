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
@Table(name="QuizesOfClass")
public class QuizOfClass {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idQuizOfClass;
       
	private Integer idClass;

    
    private Integer idQuiz;


	public Integer getIdQuizOfClass() {
		return idQuizOfClass;
	}


	public void setIdQuizOfClass(Integer idQuizOfClass) {
		this.idQuizOfClass = idQuizOfClass;
	}


	public Integer getIdClass() {
		return idClass;
	}


	public void setIdClass(Integer idClass) {
		this.idClass = idClass;
	}


	public Integer getIdQuiz() {
		return idQuiz;
	}


	public void setIdQuiz(Integer idQuiz) {
		this.idQuiz = idQuiz;
	}
    
    

}

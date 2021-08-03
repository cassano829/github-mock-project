<<<<<<< HEAD
package com.mockproject.model;

import java.sql.Date;

import javax.persistence.Column;
=======
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.model;

>>>>>>> master
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
<<<<<<< HEAD
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
=======
import javax.persistence.Table;

/**
 *
 * @author truon
 */

@Entity
@Table(name = "Reports")
public class Report {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer idReport;
    
    private Integer idAssignment;
    
    private Integer idQuiz;
    
    private Integer idClass;
    
    private Integer idUser;
    
    private Integer grade;

    public Integer getIdReport() {
        return idReport;
    }

    public void setIdReport(Integer idReport) {
        this.idReport = idReport;
    }

    public Integer getIdAssignment() {
        return idAssignment;
    }

    public void setIdAssignment(Integer idAssignment) {
        this.idAssignment = idAssignment;
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

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }
    
    
>>>>>>> master
}

//<<<<<<< HEAD
package com.mockproject.model;
//
//import java.io.Serializable;
//import java.sql.Date;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.persistence.Column;
//=======
///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.mockproject.model;
//
//>>>>>>> master
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//<<<<<<< HEAD
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.OneToMany;
//import javax.persistence.Table;
//
//import lombok.Data;<<<<<<< HEAD
//package com.mockproject.model;
//
//import java.sql.Date;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.persistence.Column;
//=======
///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.mockproject.model;
//
//>>>>>>> master

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
//
//import lombok.Data;
//
//
//@Entity
//@Data
//@Table(name="QuizesOfClass")
//public class QuizOfClass implements Serializable {
//	@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer idQuizOfClass;
//       
//    @ManyToOne
//	@JoinColumn(name="idClass")
//	private Class clas;
//
//    
//    @ManyToOne
//	@JoinColumn(name="idQuiz")
//	private Quiz quiz;
//
//
//	public Integer getIdQuizOfClass() {
//		return idQuizOfClass;
//	}
//
//
//	public void setIdQuizOfClass(Integer idQuizOfClass) {
//		this.idQuizOfClass = idQuizOfClass;
//	}
//
//
//	public Class getClas() {
//		return clas;
//	}
//
//
//	public void setClas(Class clas) {
//		this.clas = clas;
//	}
//
//
//	public Quiz getQuiz() {
//		return quiz;
//	}
//
//
//	public void setQuiz(Quiz quiz) {
//		this.quiz = quiz;
//	}
//    
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
@Table(name = "QuizesOfClass")
public class QuizOfClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idQuizOfClass;

    @ManyToOne
    @JoinColumn(name = "idQuiz")
    private Quiz quiz;

    @ManyToOne
    @JoinColumn(name = "idClass")
    private Class clas;
}

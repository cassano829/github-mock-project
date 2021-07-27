/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 *
 * @author Asus
 */
@Entity
@Data
@Table(name = "Quizes")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idQuiz;

    private int idUser;
    private int numOfQues;
    private int timeLimit;
    private boolean status;

    private String nameQuiz;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", shape = JsonFormat.Shape.STRING)
    @Column(name = "openDate")
    private String openDate;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", shape = JsonFormat.Shape.STRING)
    @Column(name = "dueDate")
    private String dueDate;

    @ManyToOne
    @JoinColumn(name = "idSubject")
    private Subject subject;

    @ManyToMany(mappedBy = "quizes", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Class> classes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "quiz")
    private Set<QuizOfStudent> quizOfStudents;

}

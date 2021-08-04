package com.mockproject.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Table(name = "QuizesOfUser")
@Getter
@Setter
public class QuizOfUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idQuizOfUser;

    @Column(nullable = false, unique = true)
    private boolean isPass;

    @Column(nullable = false, unique = true)
    private int totalCorrect;

    @Column
    private double grade;

    private Integer idUser;

    private Integer idQuiz;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", shape = JsonFormat.Shape.STRING)
    @Column(name = "submitDate")
    private String submitDate;

    @OneToMany(mappedBy = "quizOfUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<QuizDetail> quizDetails;

}

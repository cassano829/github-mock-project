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
@Table(name="Answers")
public class Answer {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAnswer;
    

    @Column(nullable = false, unique = true)
    private String content;
    
    @Column(nullable = false, unique = true)
    private boolean isCorrect;
    
    @ManyToOne
	@JoinColumn(name="idQuestion")
	private Question question;

	public Integer getIdAnswer() {
		return idAnswer;
	}

	public void setIdAnswer(Integer idAnswer) {
		this.idAnswer = idAnswer;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isCorrect() {
		return isCorrect;
	}

	public void setCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}
    
    


	
    
}

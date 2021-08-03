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
@Table(name="Questions")
public class Question {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idQuestion;
    
    @Column(nullable = false, unique = true)
    private String content;
    
    @Column(nullable = false, unique = true, updatable = false)
    private boolean status;
    
    @Column(nullable = false, unique = true, updatable = false)
    private Date createDate;

    
    @ManyToOne
	@JoinColumn(name="idQuiz")
	private Quiz quiz;

   
    @OneToMany(mappedBy = "question")
	private List<Answer> answer = new ArrayList<>();
    


	public Integer getIdQuestion() {
		return idQuestion;
	}


	public void setIdQuestion(Integer idQuestion) {
		this.idQuestion = idQuestion;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
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


	public Quiz getQuiz() {
		return quiz;
	}


	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}


	public List<Answer> getAnswer() {
		return answer;
	}


	public void setAnswer(List<Answer> answer) {
		this.answer = answer;
	}
    
}

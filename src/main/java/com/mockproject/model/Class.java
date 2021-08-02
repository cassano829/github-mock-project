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
@Table(name="Classes")
public class Class {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idClass;
    

    @Column(nullable = false, unique = true)
    private String nameClass;
    
    @Column(nullable = false, unique = true, updatable = false)
    private boolean status;
    
    @Column(nullable = false, unique = true, updatable = false)
    private Date createDate;
    
    @Column(nullable = false, unique = true, updatable = false)
    private String password;

    
    @ManyToOne
	@JoinColumn(name="idUser")
	private User user;

    
    @ManyToOne
	@JoinColumn(name="idSubject")
	private Subject subject;
    
    @OneToMany(mappedBy = "clas")
	private List<Report> reports = new ArrayList<>();
    
    @OneToMany(mappedBy = "clas")
	private List<QuizOfClass> quizOfClass = new ArrayList<>();
    
    @OneToMany(mappedBy = "clas")
	private List<AssignmentOfClass> asignmentOfClass = new ArrayList<>();
    
    @OneToMany(mappedBy = "clas")
	private List<UserOfClass> userOfClass = new ArrayList<>();


	public Integer getIdClass() {
		return idClass;
	}


	public void setIdClass(Integer idClass) {
		this.idClass = idClass;
	}


	public String getNameClass() {
		return nameClass;
	}


	public void setNameClass(String nameClass) {
		this.nameClass = nameClass;
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


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Subject getSubject() {
		return subject;
	}


	public void setSubject(Subject subject) {
		this.subject = subject;
	}
    
    

	
    
}

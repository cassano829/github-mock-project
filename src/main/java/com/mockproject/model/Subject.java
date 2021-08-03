package com.mockproject.model;

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
@Table(name="Subjects")
public class Subject {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSubject;
    

    @Column(nullable = false, unique = true)
    private String nameSubject;
    
    @Column(nullable = false, unique = true, updatable = false)
    private String createDate;

    
	private Integer idUser;

	public Integer getIdSubject() {
		return idSubject;
	}


	public void setIdSubject(Integer idSubject) {
		this.idSubject = idSubject;
	}


	public String getNameSubject() {
		return nameSubject;
	}


	public void setNameSubject(String nameSubject) {
		this.nameSubject = nameSubject;
	}


	public String getCreateDate() {
		return createDate;
	}


	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}


//	public User getUser() {
//		return user;
//	}
//
//
//	public void setUser(User user) {
//		this.user = user;
//	}
    
    
}

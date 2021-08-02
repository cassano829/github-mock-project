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
@Table(name="Assignments")
public class Assignment {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAssignment;
	
	@Column(nullable = false, unique = true)
    private String title;
	
	@Column(nullable = false, unique = true)
    private String content;
	
	@Column(nullable = false, unique = true)
    private boolean status;
	
	@Column(nullable = false, unique = true)
    private Date deadline;
	
	@Column(nullable = false, unique = true)
	private Date createDate;
    
	@OneToMany(mappedBy = "assignment")
	private List<AssignmentOfClass> assignmentOfClasses = new ArrayList<>();
	
	@OneToMany(mappedBy = "assignment")
	private List<Report> report = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name="idSubject")
	private Subject subject;
	
	@ManyToOne
	@JoinColumn(name="idUser")
	private User user;

	public Integer getIdAssignment() {
		return idAssignment;
	}

	public void setIdAssignment(Integer idAssignment) {
		this.idAssignment = idAssignment;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public List<AssignmentOfClass> getAssignmentOfClasses() {
		return assignmentOfClasses;
	}

	public void setAssignmentOfClasses(List<AssignmentOfClass> assignmentOfClasses) {
		this.assignmentOfClasses = assignmentOfClasses;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
	
    
}

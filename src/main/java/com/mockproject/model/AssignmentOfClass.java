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
@Table(name="AssignmentsOfClass")
public class AssignmentOfClass {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAssignmentOfClass;
    
    
    @ManyToOne
	@JoinColumn(name="idClass")
	private Class clas;
    
    @ManyToOne
	@JoinColumn(name="idAssignment")
	private Assignment assignment;

	public Integer getIdAssignmentOfClass() {
		return idAssignmentOfClass;
	}

	public void setIdAssignmentOfClass(Integer idAssignmentOfClass) {
		this.idAssignmentOfClass = idAssignmentOfClass;
	}

	public Class getClas() {
		return clas;
	}

	public void setClas(Class clas) {
		this.clas = clas;
	}

	public Assignment getAssignment() {
		return assignment;
	}

	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}

    
    
}

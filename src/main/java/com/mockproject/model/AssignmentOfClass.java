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
    
	private Integer idClass;
    
	private Integer idAssignment;

	public Integer getIdAssignmentOfClass() {
		return idAssignmentOfClass;
	}

	public void setIdAssignmentOfClass(Integer idAssignmentOfClass) {
		this.idAssignmentOfClass = idAssignmentOfClass;
	}

	public Integer getIdClass() {
		return idClass;
	}

	public void setIdClass(Integer idClass) {
		this.idClass = idClass;
	}

	public Integer getIdAssignment() {
		return idAssignment;
	}

	public void setIdAssignment(Integer idAssignment) {
		this.idAssignment = idAssignment;
	}

	
	
}

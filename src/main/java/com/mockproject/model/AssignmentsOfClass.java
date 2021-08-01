/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author truon
 */

@Entity
@Table(name = "AssignmentsOfClass")
public class AssignmentsOfClass {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer idAssignmentOfClass;
    
    private Integer idAssignment;
    
    private Integer idClass;

    public Integer getIdAssignmentOfClass() {
        return idAssignmentOfClass;
    }

    public void setIdAssignmentOfClass(Integer idAssignmentOfClass) {
        this.idAssignmentOfClass = idAssignmentOfClass;
    }

    public Integer getIdAssignment() {
        return idAssignment;
    }

    public void setIdAssignment(Integer idAssignment) {
        this.idAssignment = idAssignment;
    }

    public Integer getIdClass() {
        return idClass;
    }

    public void setIdClass(Integer idClass) {
        this.idClass = idClass;
    }
    
    
}

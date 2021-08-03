/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author truon
 */

@Entity
@Table(name = "AssignmentsOfUser")
public class AssignmentsOfUser {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer idAssignmentOfUser;
    
    private Integer idAssignment;
    
    private Integer idClass;
    
    private Integer idUser;
    
    private String content;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date uploadTime;
    
    private String attachments;

    public Integer getIdAssignmentOfUser() {
        return idAssignmentOfUser;
    }

    public void setIdAssignmentOfUser(Integer idAssignmentOfUser) {
        this.idAssignmentOfUser = idAssignmentOfUser;
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

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getAttachments() {
        return attachments;
    }

    public void setAttachments(String attachments) {
        this.attachments = attachments;
    }
    
    
}

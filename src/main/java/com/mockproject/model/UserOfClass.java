/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author ACER
 */
@Entity
@Data
@Table(name = "UsersOfClass")
public class UserOfClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUserOfCLass;

	private Integer idClass;
    
	private Integer idUser;

	public Integer getIdUserOfCLass() {
		return idUserOfCLass;
	}

	public void setIdUserOfCLass(Integer idUserOfCLass) {
		this.idUserOfCLass = idUserOfCLass;
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

	
    
    

}

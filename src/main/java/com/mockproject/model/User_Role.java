/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author ACER
 */
@Entity
@Data
@Table(name = "RolesOfUser")
public class User_Role {

    public User_Role() {
    }

    public User_Role(int idUser, int idRole) {
        this.idUser = idUser;
        this.idRole = idRole;
    }

    @Id
    @Column(name = "idUser")
    private int idUser;
    @Column(name = "idRole")
    private int idRole;

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

}

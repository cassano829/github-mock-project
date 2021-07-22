/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author ACER
 */
@Entity
@Data
@Table(name = "RolesOfUser")
public class User_Role {

    @Id
    private Integer idUser;
    private int idRole;
}

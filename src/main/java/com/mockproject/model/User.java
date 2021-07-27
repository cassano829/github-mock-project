/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author ACER
 */
@Entity
@Data
@Table(name = "Users")
@SQLDelete(sql = "UPDATE Users SET status = 'false' WHERE idUser=?")
@FilterDef(name = "userStatusFilter",
            parameters = @ParamDef(name = "status", type = "boolean"))
@Filter(name = "deletedProductFilter", condition = "status = :isDeleted")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUser;

    @NotBlank(message = "Fullname can't be empty")
    @NotEmpty(message = "Fullname can't be empty")
    private String fullName;

    @Column(nullable = false, unique = true)
    @Email(message = "Email format incorrect",
            regexp = "[a-z][a-z0-9_.]{4,32}@[a-z0-9]{2,}([.][a-z0-9]{2,4}){1,2}")
    private String email;
    
    @Column(nullable = false, unique = true, updatable = false)
    private String verificationCode;


    @NotBlank(message = "Password can't be empty")
    @NotEmpty(message = "Password can't be empty")
    @Size.List ({
            @Size(min=8, message="Password must be at least 8 characters"),
            @Size(max=60, message="Password must be less than 16 characters")
    })
    private String password;

    private boolean status=Boolean.TRUE;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @Column(name = "createDate")
    private String createDate;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "RolesOfUser",
            joinColumns = @JoinColumn(name = "idUser"),
            inverseJoinColumns = @JoinColumn(name = "idRole")
    )
    private Set<Role> roles = new HashSet<Role>();

    public boolean hasRole(String roleName) {
        Iterator<Role> iterator = roles.iterator();
        while (iterator.hasNext()) {
            Role role = iterator.next();
            if (role.getName().equals(roleName)) {
                return true;
            }
        }
        return false;
    }

}

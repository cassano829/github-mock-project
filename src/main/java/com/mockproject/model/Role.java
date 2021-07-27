/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 *
 * @author ACER
 */
@Entity
@Data
@Table(name = "Roles")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRole")
    private Integer id;

    @Column(name = "nameRole")
    private String name;

//    public int hashCode() {
//        return Objects.hash(this.name);
//    }
//
//    public boolean equals(Object o) {
//        if (o == this) { return true; }
//        if (o == null || !(o instanceof Role) ) { return false; }
//        return Objects.equals(this.name, ((Role) o).name);
//    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.service;

import com.mockproject.model.User_Role;
import com.mockproject.repository.User_RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ACER
 */
@Service
public class User_RoleService {

    @Autowired
    User_RoleRepository repo;

    public void save(User_Role ur){
        repo.save(ur);
    }
}

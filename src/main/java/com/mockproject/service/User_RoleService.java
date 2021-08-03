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

import java.util.Optional;

/**
 * @author ACER
 */
@Service
public class User_RoleService {

    @Autowired
    User_RoleRepository repo;

    public Optional<User_Role> findByIdUser(int idUser) {
        return repo.findById(idUser);
    }

    public void save(User_Role ur) {
        repo.save(ur);
    }

    public void updateUserRole(int idRole, int idUser) {
        Optional<User_Role> ur = findByIdUser(idUser);
        if (ur.isPresent()) {
            ur.get().setIdRole(idRole);
            repo.save(ur.get());
        }
    }
}

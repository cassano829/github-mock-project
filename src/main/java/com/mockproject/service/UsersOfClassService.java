/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.service;

import com.mockproject.model.UsersOfClass;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mockproject.repository.UsersOfClassRepository;

/**
 *
 * @author ACER
 */
@Service
public class UsersOfClassService {

    @Autowired
    UsersOfClassRepository repo;
    
    public List<UsersOfClass> findUserByIdUser(int idUser){
        return repo.findUserByIdUser(idUser);
    }
    
    public void save(UsersOfClass uol){
        repo.save(uol);
    }
    
}

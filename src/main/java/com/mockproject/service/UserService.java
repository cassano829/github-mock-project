/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.service;

import com.mockproject.model.User;
import com.mockproject.model.User_Role;
import com.mockproject.repository.UserRepository;
import com.mockproject.repository.User_RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ACER
 */
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User findUserByidUser(int id){
    	return userRepository.findUserByIdUser(id);
    }
}

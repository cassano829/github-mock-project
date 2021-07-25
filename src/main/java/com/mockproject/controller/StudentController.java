/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.controller;

import com.mockproject.security.UserDetailServiceImp;
import com.mockproject.service.User_RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author ACER
 */
@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    UserController userController;
    
    @Autowired
    UserDetailServiceImp service;

    @Autowired
    User_RoleService user_roleService;

    @GetMapping("/home")
    public String studentHomePage(Model model) {
        return userController.listByPage(model, 1);
    }

    @GetMapping("/class")
    public String studentClassPage() {
        return "studentClass";
    }

    @GetMapping("/account")
    public String studentAccountPage() {
        return "studentAccount";
    }
}

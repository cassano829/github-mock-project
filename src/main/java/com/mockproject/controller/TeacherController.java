/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.controller;

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
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    UserController userController;

    @GetMapping("/home")
    public String teacherHomePage(Model model) {
        return userController.listByPage(model, 1);
    }

    @GetMapping("/subject")
    public String teacherSubjectPage() {
        return "teacherSubject";
    }

    @GetMapping("/account")
    public String teacherAccountPage() {
        return "teacherAccount";
    }
}

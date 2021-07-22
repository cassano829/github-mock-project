/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.controller;

import com.mockproject.model.User;
import com.mockproject.model.User_Role;
import com.mockproject.security.UserDetailServiceImp;
import com.mockproject.service.User_RoleService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author ACER
 */
@Controller
public class UserController {

    @Autowired
    UserDetailServiceImp service;

    @Autowired
    User_RoleService user_roleService;

    @GetMapping("/")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/403")
    public String handleError() {
        return "403";
    }

    //admin
    @GetMapping("/admin/home")
    public String adminHomePage() {
        return "adminHome";
    }

    @GetMapping("/admin/subject")
    public String adminSubjectPage() {
        return "adminSubject";
    }

    @GetMapping("/admin/account")
    public String adminAccountPage() {
        return "adminAccount";
    }

    @GetMapping("/admin/user")
    public String adminUserPage() {
        return "adminUser";
    }

    //teacher
    @GetMapping("/teacher/home")
    public String teacherHomePage() {
        return "teacherHome";
    }

    @GetMapping("/teacher/subject")
    public String teacherSubjectPage() {
        return "teacherSubject";
    }

    @GetMapping("/teacher/account")
    public String teacherAccountPage() {
        return "teacherAccount";
    }

    //student
    @GetMapping("/student/home")
    public String studentHomePage() {
        return "studentHome";
    }

    @GetMapping("/student/class")
    public String studentClassPage() {
        return "studentClass";
    }

    @GetMapping("/student/account")
    public String studentAccountPage() {
        return "studentAccount";
    }

    @GetMapping("/student/create")
    public String signUp(Model model) {
        model.addAttribute("user", new User());
        return "sign-up";
    }

    @PostMapping("/student/save")
    public String signUp(@ModelAttribute("user") User user,
            Model model, @RequestParam("rePassword") String rePwd) {

        boolean checkEmpty = true;
        if (user.getEmail().trim().isEmpty()) {
            checkEmpty = false;
        }
        if (user.getFullName().trim().isEmpty()) {
            checkEmpty = false;
        }
        if (user.getPassword().trim().isEmpty()) {
            checkEmpty = false;
        }
        if (checkEmpty) {
            String error = "";
            boolean checkCorrect = true;
            boolean checkEmail = service.isValidEmail(user.getEmail());
            boolean checkRePwd = rePwd.matches(user.getPassword());

            if (!checkEmail) {
                checkCorrect = false;
                error = "Wrong email format! (ex: alpha123@gmail.com)";
            }
            if (!checkRePwd) {
                checkCorrect = false;
                error = "Re-password not matches!";
            }
            if (checkCorrect) {
                boolean checkExist = service.isEmailUnique(user.getEmail());
                if (!checkExist) {
                    model.addAttribute("messageError", "Email has already existed!");
                    return "sign-up";
                } else {
                    service.register(user);
                    User_Role ur = new User_Role();
                    ur.setIdRole(3); // 3 = student role
                    ur.setIdUser(user.getIdUser());
                    user_roleService.save(ur);
                    model.addAttribute("message", "Registered successfully!");
                    model.addAttribute("nofitication", "Please check your email to verify your account!");
                    return "verify";
                }
            } else {
                model.addAttribute("messageError", error);
                return "sign-up";
            }
        } else {
            model.addAttribute("messageError", "Cannot empty any fields");
            return "sign-up";
        }
    }

    @PostMapping("/student/verify")
    public String verifyAccount(@RequestParam("AUTHEN_CODE") String code, Model model) {
        boolean verified = service.verify(code);
        if (verified) {
            return "verify_success";
        } else {
            model.addAttribute("messageError", "Invalid code verify!");
            return "verify";
        }
    }
}

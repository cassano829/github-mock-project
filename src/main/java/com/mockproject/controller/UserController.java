/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.controller;

import com.mockproject.model.Role;
import com.mockproject.model.News;
import com.mockproject.model.User;
import com.mockproject.model.User_Role;
import com.mockproject.security.CustomUserDetail;
import com.mockproject.security.UserDetailServiceImp;
//import com.mockproject.service.RoleService;
//import com.mockproject.service.User_RoleService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.text.SimpleDateFormat;
//import java.util.HashMap;
//import java.util.List;
import java.util.Optional;
import java.util.Set;
import com.mockproject.service.NewsService;
import com.mockproject.service.RoleService;
import com.mockproject.service.User_RoleService;
//import com.mockproject.service.User_RoleService;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ACER
 */
@Controller
public class UserController {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Autowired
    UserDetailServiceImp service;

    @Autowired
    RoleService roleService;
    
    @Autowired
    NewsService newService;

    @Autowired
    User_RoleService user_roleService;

    @GetMapping("/")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/loginError")
    public String errorPage() {
        return "redirect:/?error";
    }

    @GetMapping("/403")
    public String handleError() {
        return "403";
    }

    //*****************************************************************************************
    // ****************************************  TEACHER **************************************
    //*****************************************************************************************

//    @GetMapping("/teacher/home")
//    public String teacherHomePage() {
//        return "teacherHome";
//    }
//
//    @GetMapping("/teacher/subject")
//    public String teacherSubjectPage() {
//        return "teacherSubject";
//    }



    //redirect to update account PAGE
//    @RequestMapping(value = "/teacher/update-account-page")
//    public String updateTeacherAccountPage(@RequestParam(name = "idUser") int idUser, Model model) {
//        //update account page
//        User user = service.loadUserByIdUser(idUser);
//
//        if (user != null) {
//            model.addAttribute("user", user);
//        }
//        return "teacher-update-account";
//    }
//
//    //handle update TEACHER'S account request
//    @PostMapping(value = "/teacher/update-account")
//    public String updateTeacherAccount(@ModelAttribute(name = "user") User user
//            , @RequestParam(name = "confirm_password") String confirmPassword
//            , Model model) {
//        //Validate data from attribute
//        Set<ConstraintViolation<User>> violations = validator.validate(user);
//        //Error map
//        HashMap<String, String> error = new HashMap<>();
//        String message = null;
//
//        //check unique email
//        boolean isEmailUnique = service.isEmailUniqueUpdate(user.getEmail(), user.getIdUser());
//        if (!isEmailUnique) {
//            error.put("emailError", "This email address was already being used");
//        }
//
//        //check match password confirm
//        boolean isPasswordMatch = user.getPassword().equalsIgnoreCase(confirmPassword);
//        if (!isPasswordMatch) {
//            error.put("confirmPasswordError", "Confirm password not match");
//        }
//
//        if (violations.isEmpty() && error.isEmpty() && isPasswordMatch) {
//            try {
//                service.updateAdminAcount(user);
//                model.addAttribute("message", "Successfully update account : " + user.getEmail());
//                return "forward:/teacher/account";
//            } catch (Exception e) {
//                e.printStackTrace();
//                message = "Error while update this account";
//            }
//        } else {
//            for (ConstraintViolation<User> violation : violations) {
//                error.put(violation.getPropertyPath() + "Error", violation.getMessageTemplate());
//            }
//        }
//
//        model.addAttribute("error", error);
//        model.addAttribute("message", message);
//        model.addAttribute("user", user);
//
//        return "forward:/teacher/update-account-page";
//    }

    //*****************************************************************************************
    //***************************************** STUDENT ***************************************
    //*****************************************************************************************


//    @GetMapping("/student/home")
//    public String studentHomePage() {
//        return "studentHome";
//    }
//
//    @GetMapping("/student/class")
//    public String studentClassPage() {
//        return "studentClass";
//    }

//    @RequestMapping("/student/account")
//    public String studentAccountPage(Model model) {
//        CustomUserDetail user = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        model.addAttribute("user", user.getUser());
//
//        return "student-account";
//    }
    
    @GetMapping("/handleException")
    public String handleException() {
        CustomUserDetail user = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user.hasRole("ADMIN")) {
            return "redirect:/admin/home";
        } else if (user.hasRole("TEACHER")) {
            return "redirect:/teacher/home";
        } else if (user.hasRole("STUDENT")) {
            return "redirect:/student/home";
        }
        return null;
    }

    @GetMapping("/page/{pageNumber}")
    public String listByPage(Model model, @PathVariable(name = "pageNumber") Integer currentPage) {
        CustomUserDetail user = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Page<News> page = newService.getListNewsByStatus(currentPage);
        int totalPages = page.getTotalPages();
        List<News> list = page.getContent();
        HashMap<Integer, String> mapNames = new HashMap<Integer, String>();

        if (list != null) {
            if (list.size() != 0) {
                for (News n : list) {
                    User u = service.getUserByIdUser(n.getIdUser());
                    mapNames.put(u.getIdUser(), u.getFullName());
                }
                model.addAttribute("currentPage", currentPage);
                model.addAttribute("totalPages", totalPages);
                model.addAttribute("mapNames", mapNames);
                model.addAttribute("listNews", list);
            } else {
                model.addAttribute("message", "Empty Announcement!");
            }
        }
        if (user.hasRole("TEACHER")) {
            return "teacherHome";
        } else if (user.hasRole("STUDENT")) {
            return "studentHome";
        }
        return null;
    }

    @GetMapping("/create")
    public String signUp(Model model) {
        model.addAttribute("user", new User());
        return "sign-up";
    }

    @PostMapping("/save")
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
            if(user.getPassword().length() < 8 || user.getPassword().length() > 16){
                checkCorrect = false;
                error = "Password length must between 8-16 characters";
            }
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

    @PostMapping("/verify")
    public String verifyAccount(@RequestParam("AUTHEN_CODE") String code, Model model) {
        boolean verified = service.verify(code);
        if (verified) {
            return "verify_success";
        } else {
            model.addAttribute("messageError", "Invalid code verify!");
            return "verify";
        }
    }

    //redirect to update account PAGE


    //handle update STUDENT'S account request
    


    //=============================== Utilities Methods

    private List<User> prepareUserList(List<User> userList) {
        if (userList != null && !userList.isEmpty()) {
            for (User u : userList) {
                try {
                    u.setCreateDate(new SimpleDateFormat("dd-MMM-yyyy")
                            .format(new SimpleDateFormat("yyyy-MM-dd").parse(u.getCreateDate())));
                    Optional<User_Role> ur = user_roleService.findByIdUser(u.getIdUser());
                    if (ur.isPresent()) {
                        Optional<Role> r = roleService.findById(ur.get().getIdRole());
                        if (r.isPresent()) {
                            u.getRoles().add(r.get());
                        }
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return userList;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.controller;

import com.mockproject.model.User;
import com.mockproject.security.UserDetailServiceImp;
import com.mockproject.service.ClassService;
import com.mockproject.model.Class;
import com.mockproject.security.CustomUserDetail;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author ACER
 */
@Controller
@RequestMapping("/teacher")
public class TeacherController {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Autowired
    UserController userController;

    @Autowired
    ClassService classService;

    @Autowired
    UserDetailServiceImp userService;

    @Autowired
    UserDetailServiceImp service;

    @GetMapping("/home")
    public String showTeacherHome(Model model) {
        return userController.listByPage(model, 1);
    }

    @GetMapping("/classPage/{pageNumber}/{id}")
    public String listByPageClassTeacher(Model model, @PathVariable(name = "pageNumber") Integer currentPage,
            @PathVariable(name = "id") String idSubject) {
        User u = new User();
        Page<Class> page = classService.getListClassRoleTeacher(currentPage, idSubject);
        int totalPages = page.getTotalPages();
        List<Class> list = page.getContent();
        if (list != null) {
            if (list.size() != 0) {
                model.addAttribute("currentPage", currentPage);
                model.addAttribute("totalPages", totalPages);
                model.addAttribute("idSubject", idSubject);
                model.addAttribute("nameUser", u.getFullName());
                model.addAttribute("listClass", list);
            } else {
                model.addAttribute("message", "Empty Class!");
            }
        }
        return "teacherClass";
    }

    @GetMapping("/subject/deleteClass/{id}")
    public String deleteClass(@PathVariable(name = "id") Integer id) {
        String idSubject = classService.delete(id);
        return "redirect:/teacher/subject/" + idSubject;
    }

    @GetMapping("/subject/restoreClass/{id}")
    public String restoreClass(@PathVariable(name = "id") Integer id) {
        String idSubject = classService.restore(id);
        return "redirect:/teacher/subject/" + idSubject;
    }

    @GetMapping("/subject/{id}")
    public String enrollSubject(@PathVariable(name = "id") String idSubject, Model model) {
        return listByPageClassTeacher(model, 1, idSubject);
    }

//    @GetMapping("/subject")
//    public String showSubject() {
//        return "teacherSubject";
//    }

    @GetMapping("/subject/editClass/{id}")
    public ModelAndView editClassFormPage(@PathVariable(name = "id") Integer id) {
        ModelAndView mav = new ModelAndView("teacherEditClass");
        Class c = classService.getClassById(id);
        mav.addObject("class", c);
        return mav;
    }

    @PostMapping("/subject/editClass")
    public String editClassPage(@ModelAttribute("class") Class c, Model model) {
        boolean checkEmpty = true;
        String error = "";
        if ("".trim().equals(c.getNameClass())) {
            error += "Class name cannot be blank! ";
            checkEmpty = false;
        }
        if ("".trim().equals(c.getPassword())) {
            error += "Password cannot be blank!";
            checkEmpty = false;
        }
        if (checkEmpty) {
            classService.save(c);
            model.addAttribute("message", "Update successful!");
        } else {
            model.addAttribute("msgError", error);
        }
        return "teacherEditClass";
    }

    @GetMapping("/subject/createClass/{id}")
    public String createClassFormPage(Model model, @PathVariable(name = "id") String idSubject) {
        Class c = new Class();
        c.setIdSubject(idSubject);
        model.addAttribute("class", c);
        return "teacherCreateClass";
    }

    @PostMapping("/subject/createClass")
    public String createClassPage(@ModelAttribute("class") Class c, Model model) {
        boolean checkEmpty = true;
        String error = "";
        if ("".trim().equals(c.getNameClass())) {
            error += "Class name cannot be blank! ";
            checkEmpty = false;
        }
        if ("".trim().equals(c.getPassword())) {
            error += "Password cannot be blank!";
            checkEmpty = false;
        }
        if (checkEmpty) {
            c.setStatus(true);
            classService.save(c);
            return "redirect:/teacher/subject/" + c.getIdSubject();
        } else {
            model.addAttribute("msgError", error);
            return "teacherCreateClass";
        }
    }

    @GetMapping("/account")
    public String teacherAccountPage(Model model) {
        CustomUserDetail user = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user.getUser());

        return "teacher-account";
    }

    @RequestMapping(value = "/update-account-page")
    public String updateTeacherAccountPage(@RequestParam(name = "idUser") int idUser, Model model) {
        //update account page
        User user = service.loadUserByIdUser(idUser);

        if (user != null) {
            model.addAttribute("user", user);
        }
        return "teacher-update-account";
    }

    @PostMapping(value = "/update-account")
    public String updateTeacherAccount(@ModelAttribute(name = "user") User user,
            @RequestParam(name = "confirm_password") String confirmPassword,
            Model model) {
        //Validate data from attribute
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        //Error map
        HashMap<String, String> error = new HashMap<>();
        String message = null;

        //check unique email
        boolean isEmailUnique = service.isEmailUniqueUpdate(user.getEmail(), user.getIdUser());
        if (!isEmailUnique) {
            error.put("emailError", "This email address was already being used");
        }

        //check match password confirm
        boolean isPasswordMatch = user.getPassword().equalsIgnoreCase(confirmPassword);
        if (!isPasswordMatch) {
            error.put("confirmPasswordError", "Confirm password not match");
        }

        if (violations.isEmpty() && error.isEmpty() && isPasswordMatch) {
            try {
                service.updateAdminAcount(user);
                model.addAttribute("message", "Successfully update account : " + user.getEmail());
                return "forward:/teacher/account";
            } catch (Exception e) {
                e.printStackTrace();
                message = "Error while update this account";
            }
        } else {
            for (ConstraintViolation<User> violation : violations) {
                error.put(violation.getPropertyPath() + "Error", violation.getMessageTemplate());
            }
        }

        model.addAttribute("error", error);
        model.addAttribute("message", message);
        model.addAttribute("user", user);

        return "forward:/teacher/update-account-page";
    }
}

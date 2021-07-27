/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.controller;

import com.mockproject.model.Role;
import com.mockproject.model.User;
import com.mockproject.model.User_Role;
import com.mockproject.security.CustomUserDetail;
import com.mockproject.security.UserDetailServiceImp;
import com.mockproject.service.RoleService;
import com.mockproject.service.User_RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    @RequestMapping("/admin/account")
    public String adminAccountPage(Model model) {
        CustomUserDetail user = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        model.addAttribute("admin", user.getUser());

        return "admin-account";
    }

    @RequestMapping("/admin/user")
    public String adminUserPage(Model model) {

        List<User> userList = service.loadAllUsers();
        userList = prepareUserList(userList);

        model.addAttribute("roleList", roleService.getRoleList());
        model.addAttribute("userList", userList);

        return "admin-users";
    }

    //redirect to register teacher page
    @RequestMapping("/admin/register-teacher-page")
    public String registerTeacherPage(@RequestParam(name = "user", required = false) User teacher
            , @RequestParam(name = "error", required = false) HashMap<String, String> error
            , @RequestParam(name = "message", required = false) String message
            , Model model) {

        model.addAttribute("error", error);
        model.addAttribute("message", message);
        model.addAttribute("user", teacher);

        return "admin-add-teacher";
    }

    //handle register teacher request from client
    @PostMapping(value = "/admin/register-teacher")
    public String registerTeacher(@ModelAttribute(name = "user") User teacher, Model model) {

        //Validate data from attribute
        Set<ConstraintViolation<User>> violations = validator.validate(teacher);
        //Error map
        HashMap<String, String> error = new HashMap<>();
        String message = null;
        //check unique email
        boolean isEmailUnique = service.isEmailUnique(teacher.getEmail());
        if (!isEmailUnique) {
            error.put("emailError", "This email address was already being used");
        }

        if (violations.isEmpty() && error.isEmpty()) {
            Optional<Role> teacherRole = roleService.findByName("TEACHER");

            System.out.println(teacherRole.get().getId() + " : " + teacherRole.get().getName());

            if (teacherRole.isPresent()) {
                try {
                    service.registerTeacher(teacher);
                    user_roleService.save(new User_Role(teacher.getIdUser(), teacherRole.get().getId()));

                    message = "Complete register new user : " + teacher.getFullName();
                    System.out.println(message);

                    model.addAttribute("message", message);
                    return "admin-user";
                } catch (Exception e) {
                    e.printStackTrace();
                    message = "Error occured while registering this teacher";
                }
            }
        } else {
            for (ConstraintViolation<User> violation : violations) {
                error.put(violation.getPropertyPath() + "Error", violation.getMessageTemplate());
            }
        }
        //if failed
        model.addAttribute("error", error);
        model.addAttribute("message", message);
        model.addAttribute("user", teacher);

        return "forward:/user/register-teacher-page";
    }

    //redirect to update account PAGE
    @RequestMapping(value = "/admin/update-account-page")
    public String updateAccountPage(@RequestParam(name = "idUser") int idUser, Model model) {
        //update account page
        User user = service.loadUserByIdUser(idUser);

        if (user != null) {
            model.addAttribute("user", user);
        }

        return "admin-update-account";
    }

    //handle update ADMIN'S account request
    @PostMapping(value = "/admin/update-account")
    public String updateAccount(@ModelAttribute(name = "user") User user
            , @RequestParam(name = "confirm_password") String confirmPassword
            , Model model) {
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
                return "forward:/admin/account";
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

        return "forward:/admin/update-account-page";
    }


    //handle search user request from client
    @PostMapping(value = "/admin/search-user")
    public String searchUser(@RequestParam(name = "email") String email,
                             @RequestParam(name = "roleId") int roleId,
                             @RequestParam(name = "status") Boolean status,
                             Model model) {
        List<User> list = null;

        Optional<Role> role = roleService.findById(roleId);
        if(role.isPresent()){
            list = service.searchUser(email, role.get(), status);
        }
        if (list.isEmpty()) {
            model.addAttribute("message","Sorry, can't find any user");

        }
        list = prepareUserList(list);
        model.addAttribute("roleList", roleService.getRoleList());
        model.addAttribute("userList", list);

        return "admin-users";
    }

    @GetMapping(value = "/admin/update-user")
    public String updateUserPage(@RequestParam(name = "idUser")int idUser,
                             Model model){

        model.addAttribute("user",service.loadUserByIdUserNonFilter(idUser));
        model.addAttribute("roleList",roleService.getRoleList());

        return "admin-user-update";
    }

    @PostMapping(value = "/admin/update-user")
    public String updateUser(@ModelAttribute(name = "userInfo")User user,
                             @RequestParam(name = "roleId")int roleId,
                             Model model){
        user.setPassword("administrator");
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
        if (violations.isEmpty() && error.isEmpty()){
            try {
                service.updateUserAccount(user);
                user_roleService.updateUserRole(roleId,user.getIdUser());
                model.addAttribute("message", "Successfully update account : " + user.getEmail());

                return "redirect:/admin/user";
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
        model.addAttribute("roleList",roleService.getRoleList());
        model.addAttribute("roleId",roleId);

        return "admin-user-update";
    }


    //*****************************************************************************************
    // ****************************************  TEACHER **************************************
    //*****************************************************************************************


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


    //*****************************************************************************************
    //***************************************** STUDENT ***************************************
    //*****************************************************************************************


    @GetMapping("/student/home")
    public String studentHomePage() {
        return "studentHome";
    }

    @GetMapping("/student/class")
    public String studentClassPage() {
        return "studentClass";
    }

    @RequestMapping("/student/account")
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

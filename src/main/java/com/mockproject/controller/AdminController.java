/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.controller;

import com.mockproject.model.News;
import com.mockproject.model.Role;
import com.mockproject.model.User;
import com.mockproject.model.User_Role;
import com.mockproject.security.CustomUserDetail;
import com.mockproject.security.UserDetailServiceImp;
import com.mockproject.service.NewsService;
import com.mockproject.service.RoleService;
import com.mockproject.service.User_RoleService;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
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
@RequestMapping("/admin")
public class AdminController {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Autowired
    UserDetailServiceImp service;

    @Autowired
    NewsService newService;

    @Autowired
    RoleService roleService;

    @Autowired
    User_RoleService user_roleService;

    @GetMapping("/deleteNews/{id}")
    public String deleteNews(@PathVariable(name = "id") Integer id) {
        newService.delete(id);
        return "redirect:/admin/home";
    }

    @GetMapping("/restoreNews/{id}")
    public String restoreNews(@PathVariable(name = "id") Integer id) {
        newService.restore(id);
        return "redirect:/admin/home";
    }

    @GetMapping("/editNews/{id}")
    public ModelAndView editNewsForm(@PathVariable(name = "id") Integer id) {
        ModelAndView mav = new ModelAndView("adminEditNews");
        News news = newService.getNewsById(id);
        mav.addObject("news", news);
        return mav;
    }

    @PostMapping("/editNews")
    public String editNews(@ModelAttribute("news") News news, Model model) {
        boolean checkEmpty = true;
        String error = "";
        if ("".trim().equals(news.getTitle())) {
            error += "Title cannot be blank! ";
            checkEmpty = false;
        }
        if ("".trim().equals(news.getInfor())) {
            error += "Content cannot be blank!";
            checkEmpty = false;
        }
        if (checkEmpty) {
            newService.save(news);
            model.addAttribute("message", "Update successful!");
        } else {
            model.addAttribute("msgError", error);
        }
        return "adminEditNews";
    }

    @PostMapping("/createNews")
    public String createNews(@ModelAttribute("news") News news, Model model) {
        boolean checkEmpty = true;
        String error = "";
        if ("".trim().equals(news.getTitle())) {
            error += "Title cannot be blank! ";
            checkEmpty = false;
        }
        if ("".trim().equals(news.getInfor())) {
            error += "Content cannot be blank!";
            checkEmpty = false;
        }
        if (checkEmpty) {
            news.setStatus(true);
            newService.save(news);
            return "redirect:/admin/home";
        } else {
            model.addAttribute("msgError", error);
            return "adminCreateNews";
        }
    }

    @GetMapping("/createNews")
    public String createNewsForm(Model model) {
        model.addAttribute("news", new News());
        return "adminCreateNews";
    }

    @GetMapping("/home")
    public String showHome(Model model) {
        return listByPageAdmin(model, 1);
    }

    @GetMapping("/pageAdmin/{pageNumber}")
    public String listByPageAdmin(Model model, @PathVariable(name = "pageNumber") Integer currentPage) {
        Page<News> page = newService.getListNews(currentPage);
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
        return "adminHome";
    }

//    @GetMapping("/subject")
//    public String showSubject() {
//        return "adminSubject";
//    }

    @RequestMapping("/account")
    public String adminAccountPage(Model model) {
        CustomUserDetail user = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user.getUser());
        return "admin-account";
    }

    @RequestMapping("/user")
    public String adminUserPage(Model model) {

        List<User> userList = service.loadAllUsers();
        userList = prepareUserList(userList);

        model.addAttribute("roleList", roleService.getRoleList());
        model.addAttribute("userList", userList);

        return "admin-users";
    }

    @GetMapping("/register-teacher-page")
    public String registerTeacherPage(Model model) {
        return "admin-create-teacher";
    }

    @PostMapping(value = "/register-teacher")
    public String registerTeacher(@ModelAttribute(name = "user") User teacher,
            @RequestParam(name = "confirm_password") String confirmPassword,
            Model model) {

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
        //check match password confirm
        boolean isPasswordMatch = teacher.getPassword().equalsIgnoreCase(confirmPassword);
        if (!isPasswordMatch) {
            error.put("confirmPasswordError", "Confirm password not match");
        }
        if (violations.isEmpty() && error.isEmpty() && isPasswordMatch) {
            Optional<Role> teacherRole = roleService.findByName("TEACHER");
            if (teacherRole.isPresent()) {
                try {
                    teacher.setIdUser(service.registerTeacher(teacher));
                    System.out.println("Insert value : " + teacher.getIdUser() + " roleId : " + teacherRole.get().getId());
                    user_roleService.save(new User_Role(teacher.getIdUser(), teacherRole.get().getId()));

                    message = "Complete register new user : " + teacher.getFullName();
                    System.out.println(message);

                    model.addAttribute("message", message);
                    return "forward:/admin/user";
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

        return "admin-create-teacher";
    }

    @RequestMapping(value = "/update-account-page")
    public String updateAdminAccountPage(@RequestParam(name = "idUser") int idUser, Model model) {
        //update account page
        User user = service.loadUserByIdUser(idUser);
        if (user != null) {
            model.addAttribute("user", user);
        }
        return "admin-update-account";
    }

    @PostMapping(value = "/update-account")
    public String updateAdminAccount(@ModelAttribute(name = "user") User user,
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

        return "admin-update-account";
    }

    @PostMapping(value = "/search-user")
    public String searchUser(@RequestParam(name = "email") String email,
            @RequestParam(name = "roleId") int roleId,
            @RequestParam(name = "status") Boolean status,
            Model model) {
        List<User> list = null;

        Optional<Role> role = roleService.findById(roleId);
        if (role.isPresent()) {
            list = service.searchUser(email, role.get(), status);
        }
        if (list.isEmpty()) {
            model.addAttribute("message", "Sorry, can't find any user");

        }
        list = prepareUserList(list);
        model.addAttribute("roleList", roleService.getRoleList());
        model.addAttribute("userList", list);

        return "admin-users";
    }

    @GetMapping(value = "/update-user")
    public String updateUserPage(@RequestParam(name = "idUser") int idUser,
            Model model) {

        model.addAttribute("user", service.loadUserByIdUserNonFilter(idUser));
        model.addAttribute("roleList", roleService.getRoleList());

        return "admin-user-update";
    }

    @PostMapping(value = "/update-user")
    public String updateUser(@ModelAttribute(name = "userInfo") User user,
            @RequestParam(name = "roleId") int roleId,
            Model model) {
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
        if (violations.isEmpty() && error.isEmpty()) {
            try {
                service.updateUserAccount(user);
                user_roleService.updateUserRole(roleId, user.getIdUser());
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
        model.addAttribute("roleList", roleService.getRoleList());
        model.addAttribute("roleId", roleId);

        return "admin-user-update";
    }

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

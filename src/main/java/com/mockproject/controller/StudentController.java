/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.controller;
import com.mockproject.service.QuizService;
import java.util.Date;
import org.springframework.data.domain.PageRequest;
import com.mockproject.model.UsersOfClass;
import com.mockproject.security.CustomUserDetail;
import com.mockproject.security.UserDetailServiceImp;
import com.mockproject.service.ClassService;
import com.mockproject.service.UsersOfClassService;
import com.mockproject.service.User_RoleService;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.mockproject.model.Class;
import com.mockproject.model.User;
import com.mockproject.repository.UserRepository;
import java.util.HashMap;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author ACER
 */


@Controller
@RequestMapping("/student")
public class StudentController {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Autowired
    UserController userController;

    @Autowired
    UserDetailServiceImp userService;

    @Autowired
    ClassService classService;

    @Autowired
    UsersOfClassService uolService;

    @Autowired
    User_RoleService user_roleService;

    @Autowired
    QuizService quizService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/quiz/showQuizesStudent/{idClass}/{page}")
    public String viewQuizesOfStudentPage(Model model, @PathVariable("idClass") int idClass, @PathVariable("page") int page, HttpSession session) {
        model.addAttribute("quizes", quizService.searchQuiz("", idClass, PageRequest.of(page - 1, 4)));
        model.addAttribute("page", page);
        session.setAttribute("class", classService.getClassById(idClass));
        model.addAttribute("today", new Date());
        return "student-quiz-list";
    }

    @GetMapping("/home")
    public String showHome(Model model) {
        return userController.listByPage(model, 1);
    }

    @GetMapping("/class")
    public String showClasses(Model model) {
        String keyword = null;
        return listByPageClass(model, 1, keyword);
    }

    @GetMapping("/enroll/{idClass}")
    public String enrollClass(@PathVariable(name = "idClass") int idClass, Model model) {
        CustomUserDetail user = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Class c = classService.getClassById(idClass);
        int idUser = c.getIdUser();
        User u = userService.getUserByIdUser(idUser); //teacher
        String name = u.getFullName();

        List<UsersOfClass> listUserEnrolled = uolService.findUserByIdUser(user.getUser().getIdUser());
        for (UsersOfClass uol : listUserEnrolled) {
            if (idClass == uol.getIdClass()) {
                return "redirect:/student/assignment/" + idClass;
            }
        }
        model.addAttribute("nameTeacher", name);
        model.addAttribute("class", c);
        return "studentEnroll";
    }

    @PostMapping("/confirmEnroll/{id}")
    public String confirmEnrollClass(@PathVariable("id") int idClass,
            @RequestParam(value = "password") String pwd, Model model) {
        CustomUserDetail user = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Class cl = classService.getClassById(idClass);
        User u = userService.getUserByIdUser(cl.getIdUser()); //teacher

        UsersOfClass uol = new UsersOfClass();
        if (cl.getPassword().equals(pwd)) {
            uol.setIdClass(idClass);
            uol.setIdUser(user.getUser().getIdUser());
            uolService.save(uol);
            model.addAttribute("idClass", uol.getIdClass());
            return "redirect:/student/assignment/" + uol.getIdClass();
        } else {
            model.addAttribute("class", cl);
            model.addAttribute("nameTeacher", u.getFullName());
            model.addAttribute("msgError", "Invalid password");
            return "studentEnroll";
        }
    }

    @GetMapping("/search")
    public String searchClassByName(@RequestParam("keyword") String keyword, Model model) {
        model.addAttribute("keyword", keyword);
        return listByPageClass(model, 1, keyword);
    }

    @GetMapping("/classPage/{pageNumber}")
    public String listByPageClass(Model model, @PathVariable(name = "pageNumber") Integer currentPage,
            @Param("keyword") String keyword) {
        CustomUserDetail user = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Page<Class> page = classService.findClassWithName(currentPage, keyword);
        int totalPages = page.getTotalPages();
        List<Class> list = page.getContent();

        HashMap<Integer, Integer> classOfUser = new HashMap<Integer, Integer>();
        List<UsersOfClass> usersOfClass = uolService.findUserByIdUser(user.getUser().getIdUser());
        HashMap<Integer, String> mapNames = new HashMap<Integer, String>();

        if (list != null) {
            if (list.size() != 0) {
                for (Class c : list) {
                    User u = userService.getUserByIdUser(c.getIdUser());
                    mapNames.put(c.getIdUser(), u.getFullName());
                }

                for (UsersOfClass uol : usersOfClass) {
                    classOfUser.put(uol.getIdClass(), uol.getIdUser());
                }
                model.addAttribute("currentPage", currentPage);
                model.addAttribute("totalPages", totalPages);
                model.addAttribute("listClass", list);
                model.addAttribute("keyword", keyword);
                model.addAttribute("mapClassEnrolled", classOfUser);
                model.addAttribute("mapNames", mapNames);
            } else {
                model.addAttribute("message", "Empty Class!");
            }
        }
        return "studentClass";
    }

    @RequestMapping("/account")
    public String studentAccountPage(Model model) {
        CustomUserDetail user = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user.getUser());

        return "student-account";
    }

    @RequestMapping(value = "/update-account-page")
    public String updateStudentAccountPage(@RequestParam(name = "idUser") int idUser, Model model) {
        //update account page
        User user = userService.loadUserByIdUser(idUser);

        if (user != null) {
            model.addAttribute("user", user);
        }
        return "student-update-account";
    }

    @PostMapping(value = "/update-account")
    public String updateStudentAccount(@ModelAttribute(name = "user") User user,
            @RequestParam(name = "confirm_password") String confirmPassword,
            Model model) {
        //Validate data from attribute
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        //Error map
        HashMap<String, String> error = new HashMap<>();
        String message = null;

        //check unique email
        boolean isEmailUnique = userService.isEmailUniqueUpdate(user.getEmail(), user.getIdUser());
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
                userService.updateAdminAcount(user);
                model.addAttribute("message", "Successfully update account : " + user.getEmail());
                return "forward:/student/account";
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

        return "forward:/student/update-account-page";
    }
}

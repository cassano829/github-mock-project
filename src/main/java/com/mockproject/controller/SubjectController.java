/* LiemNguyen created on 16/07/2021*/

package com.mockproject.controller;


import com.mockproject.model.Subject;
import com.mockproject.security.CustomUserDetail;
import com.mockproject.security.UserDetailServiceImp;
import com.mockproject.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping(value = "/subject")
public class SubjectController {

    @Autowired
    SubjectService service;

    @Autowired
    UserDetailServiceImp userService;

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @RequestMapping(value = "/user/")
    public String subjectUserPage( Model model){
        List<Subject> subjectList = service.searchByName("");

        model.addAttribute("subject_list", subjectList);
        return "subject";
    }

    @RequestMapping("/admin/")
    public String subjectAdminPage(@RequestParam(name = "error", required = false) HashMap<String, String> error
            , @RequestParam(name = "subject_add", required = false) Subject addSubject
            , @RequestParam(name = "message", required = false) String message
            , Model model) {

        List<Subject> list = service.searchByName("");
        for (Subject s : list) {
            try {
                s.setCreateDate(new SimpleDateFormat("dd-MMM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(s.getCreateDate())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (error != null) {
            System.out.println("map size after redirect stage : " + error.size());
            model.addAttribute("error", error);
            model.addAttribute("subject_add", addSubject);
        }
        model.addAttribute("message", message);
        System.out.println(message);
        model.addAttribute("subject_list", list);

        return "admin-subject";
    }

    @PostMapping(value = "/admin/add")
    public String addSubject(Subject subject, Model model) {

        //validation
        Set<ConstraintViolation<Subject>> violations = validator.validate(subject);
        //Error map
        HashMap<String, String> error = new HashMap<>();

        //check unique subject id
        subject.setIdSubject(subject.getIdSubject().toUpperCase());
        Optional<Subject> test = service.findByIdSubject(subject.getIdSubject());

        if (test.isPresent()) {
            error.put("idSubjectError", "Subject Id was already being used, try another");
        }

        if (violations.isEmpty() && !test.isPresent()) {
            CustomUserDetail user = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            System.out.println(user.getUser().getEmail());
            int id = userService.getUserByUsername(user.getUser().getEmail()).getIdUser();
            subject.setIdUser(id);
            service.save(subject);
        } else {
            for (ConstraintViolation<Subject> violation : violations) {
                error.put(violation.getPropertyPath() + "Error", violation.getMessageTemplate());
                System.out.println(violation.toString());
            }
        }
        model.addAttribute("error", error);
        model.addAttribute("subject_add", subject);
        System.out.println("Map size : " + error.size());


        return "forward:/subject/admin/";
    }

    @GetMapping(value = "/admin/update")
    public String loadUpdateSubject(@RequestParam("idSubject") String idSubject, Model model) {
        Optional<Subject> subj = service.findByIdSubject(idSubject);

        model.addAttribute("subject", subj.get());
        return "admin-update-subject";
    }

    @PostMapping("/admin/update")
    public String updateSubject(@ModelAttribute("subject") Subject subject, Model model) {

        //validation
        Set<ConstraintViolation<Subject>> violations = validator.validate(subject);
        if (violations.isEmpty()) {
            String id = subject.getIdSubject();

            subject.setIdSubject(id);
            service.save(subject);
        } else {
            String message = "";
            for (ConstraintViolation<Subject> violation : violations) {
                message += "\n" + violation.getMessage();
            }
            model.addAttribute("message", message);
            System.out.println(message);
        }
        return "redirect:/subject/admin/";
    }

    @GetMapping("/user/search")
    public String searchSubjectByName(@RequestParam("searchName") String subjectName, Model model) {

        List<Subject> list = service.searchByName(subjectName);
        if (list.isEmpty()) {
            for (Subject s : list) {
                try {
                    s.setCreateDate(new SimpleDateFormat("dd-MMM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(s.getCreateDate())));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            model.addAttribute("message", "Sorry, can't find any subject");
        } else {
            model.addAttribute("subject_list", list);
        }
        return "admin-subject";
    }

    @GetMapping("/admin/delete")
    public String deleteById(String idSubject, Model model) {
        try {
            service.delete(idSubject);
            return "forward:/subject/admin/?message=Deleted subject : " + idSubject;
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return "forward:/subject/admin/?message=Failed to delete - Subject ID not found : " + idSubject;

        }
    }

}

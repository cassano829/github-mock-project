/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.controller;

import com.mockproject.security.CustomUserDetail;
import com.mockproject.service.ClassService;
import com.mockproject.service.QuizService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.mockproject.model.Class;
import com.mockproject.model.User;
import com.mockproject.repository.UserRepository;
import java.util.HashMap;
import java.util.Map;
import org.springframework.data.domain.Sort;

/**
 *
 * @author Asus
 */
@Controller
public class StudentController {

    @Autowired
    ClassService classService;
    
    @Autowired
    QuizService quizService;

    @Autowired
    UserRepository userRepository;
    
    @GetMapping("/student/class/viewClassesOfStudent/{page}")
    public String viewClassesOfStudentPage(Model model,@PathVariable("page") int page) {
        CustomUserDetail userDetail = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Page<Class> classes= classService.getAllClassByIdStudent(userDetail.getUser().getIdUser(),PageRequest.of(page-1,4));
        Map<Integer,User> teacherOfClass=new HashMap<>();
        for(Class aClass:classes){
            teacherOfClass.put(aClass.getIdClass(), userRepository.getById(aClass.getIdUser()));
        }
        model.addAttribute("classes", classes);
        model.addAttribute("teacherOfClass", teacherOfClass);
        model.addAttribute("page", page);
        return "student-class";
    }
    
    @GetMapping("/student/quiz/showQuizesStudent/{idClass}/{page}")
    public String viewQuizesOfStudentPage(Model model,@PathVariable("idClass") int idClass,@PathVariable("page") int page){
        model.addAttribute("quizes", quizService.searchQuiz("",idClass,PageRequest.of(page-1,8)));
        model.addAttribute("page", page);
        model.addAttribute("class", classService.findClassById(idClass));
        model.addAttribute("today",new Date());
        return "student-quiz-list";
    }
    
    
}

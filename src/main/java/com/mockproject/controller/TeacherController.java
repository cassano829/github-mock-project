/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.controller;

import com.mockproject.security.CustomUserDetail;
import com.mockproject.service.ClassSerivice;
import com.mockproject.service.QuizOfStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.mockproject.model.Quiz;
import com.mockproject.service.QuizService;
import com.mockproject.service.SubjectService;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import com.mockproject.model.Class;
import java.util.Set;
import javax.servlet.http.HttpSession;
/**
 *
 * @author Asus
 */
@Controller
@RequestMapping("/teacher")
public class TeacherController {
    
    @Autowired
    QuizOfStudentService quizOfStudentService;

    @Autowired
    ClassSerivice classSerivice;
    
    @Autowired
    SubjectService subjectService;
    
    @Autowired
    QuizService quizService;
    
    @GetMapping("/subject/showAllSubjectOfTeacher")
    public String showAllSubject(Model model){
        model.addAttribute("listSubject", subjectService.getAllSubject());
        return "teacher-subject";
    }
    
    @GetMapping("/class/showClassOfTeacher/{idSubject}")
    public String showClassOfTeacher(Model model,@PathVariable("idSubject") String idSubject,HttpSession session){
        CustomUserDetail userDetail = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Class> classes = classSerivice.getListClassByIdTeacherAndIdSubject(userDetail.getUser().getIdUser(),idSubject);
        model.addAttribute("classesOfTeacher", classes);
        model.addAttribute("idSubject", idSubject);
        return "teacher-class";
    }
    
    
    @GetMapping("/quiz/showListQuiz/{idSubject}/{idClass}")
    public String showListQuizOfSubjectOfClass(HttpSession session,Model model,@PathVariable("idClass") int idClass,@PathVariable("idSubject") String idSubject) {
        List<Quiz> quizs = quizService.getAllQuizByIdClassAndIdSubject(idClass, idSubject);
        model.addAttribute("quizes", quizs);
        model.addAttribute("idClass", idClass);
        model.addAttribute("className",classSerivice.findClassById(idClass).getNameClass());
        return "teacher-quiz";
    }

    
//    @GetMapping("/quiz/viewListQuizStudent")
//    public String viewListQuizOfStudent(HttpServletRequest request,Model model){
//        int idQuiz=Integer.parseInt(request.getParameter("idQuiz"));
//        model.addAttribute("quizOfStudents",quizOfStudentService.getListQuizOfStudentByIdQuiz(idQuiz));
//        return "quizOfClass";
//    }
    
    
}

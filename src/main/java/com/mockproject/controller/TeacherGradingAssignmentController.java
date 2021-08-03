/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.controller;

import com.mockproject.model.AssignmentsOfClass;
import com.mockproject.model.AssignmentsOfUser;
import com.mockproject.model.Report;
import com.mockproject.model.User;
import com.mockproject.repository.AssignmentClassRepository;
import com.mockproject.repository.AssignmentOfClassRepository;
import com.mockproject.repository.AssignmentReportRepository;
import com.mockproject.repository.AssignmentRepository;
import com.mockproject.repository.AssignmentsOfUserRepository;
import com.mockproject.repository.UserRepository;
import com.mockproject.security.CustomUserDetail;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author truon
 */

@Controller
@RequestMapping("/teacher/assignment/grade")
public class TeacherGradingAssignmentController {
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    AssignmentRepository assignmentRepository;
    
    @Autowired
    AssignmentClassRepository assignmentClassRepository;
    
    @Autowired
    AssignmentOfClassRepository assignmentOfClassRepository;
    
    @Autowired
    AssignmentsOfUserRepository assignmentsOfUserRepository;
    
    @Autowired
    AssignmentReportRepository assignmentReportRepository;
    
    @GetMapping("/{id}")
    public String gradingClass(@PathVariable("id") Integer idAssignment, HttpSession session, Authentication authentication, Model model){
        session.setAttribute("teacherCurrentIdAssignment", idAssignment);
        Map<Integer, String> map = new HashMap<>();
        for(com.mockproject.model.Class c :assignmentClassRepository.getListClassByIdAssignment(idAssignment)){
            map.put(c.getIdClass(), c.getNameClass());
        }
        
        Integer idUser = ((CustomUserDetail) authentication.getPrincipal()).getUser().getIdUser();
        
        model.addAttribute("mapClasses", map);
        model.addAttribute("assignmentData", assignmentRepository.findById(idAssignment).get());
        //model.addAttribute("listAssignmentOfClass", assignmentOfClassRepository.findByIdAssignment(idAssignment));
        model.addAttribute("listAssignmentOfClass", assignmentOfClassRepository.customFindByIdAssignmentAndIdUser(idAssignment, idUser));
        return "teacherAssignmentCountPage";
    }
    
    @GetMapping("/class")
    public String gradingClassList(AssignmentsOfClass assignmentsOfClass, HttpSession session, Authentication authentication, Model model){
        Integer idAssignment = (Integer) session.getAttribute("teacherCurrentIdAssignment");
        session.setAttribute("teacherCurrentIdClass", assignmentsOfClass.getIdClass());
        
        Map<Integer, User> mapUser = userRepository.findAll().stream().collect(Collectors.toMap(User::getIdUser, user -> user));
        Map<Integer, String> map = new HashMap<>();
        for(com.mockproject.model.Class c :assignmentClassRepository.getListClassByIdAssignment(idAssignment)){
            map.put(c.getIdClass(), c.getNameClass());
        }
        
        Integer idUser = ((CustomUserDetail) authentication.getPrincipal()).getUser().getIdUser();
        
        model.addAttribute("mapUser", mapUser);
        model.addAttribute("mapClasses", map);
        model.addAttribute("assignmentData", assignmentRepository.findById(idAssignment).get());
        //model.addAttribute("listAssignmentOfClass", assignmentOfClassRepository.findByIdAssignment(idAssignment));
        model.addAttribute("listAssignmentOfClass", assignmentOfClassRepository.customFindByIdAssignmentAndIdUser(idAssignment, idUser));
        model.addAttribute("assignmentOfUsers", assignmentsOfUserRepository.findByIdAssignmentAndIdClass(idAssignment, assignmentsOfClass.getIdClass(), PageRequest.of(0, 5)));
        return "teacherAssignmentCountPage";
    }
    
    @GetMapping("/class/page/{id}")
    public String gradingClassListPage(@PathVariable("id") Integer page, HttpSession session, Authentication authentication, Model model){
        Integer idAssignment = (Integer) session.getAttribute("teacherCurrentIdAssignment");
        Integer idClass = (Integer) session.getAttribute("teacherCurrentIdClass");
        
        Map<Integer, User> mapUser = userRepository.findAll().stream().collect(Collectors.toMap(User::getIdUser, user -> user));
        Map<Integer, String> map = new HashMap<>();
        for(com.mockproject.model.Class c :assignmentClassRepository.getListClassByIdAssignment(idAssignment)){
            map.put(c.getIdClass(), c.getNameClass());
        }
        
        Integer idUser = ((CustomUserDetail) authentication.getPrincipal()).getUser().getIdUser();
        
        model.addAttribute("mapUser", mapUser);
        model.addAttribute("mapClasses", map);
        model.addAttribute("assignmentData", assignmentRepository.findById(idAssignment).get());
        //model.addAttribute("listAssignmentOfClass", assignmentOfClassRepository.findByIdAssignment(idAssignment));
        model.addAttribute("listAssignmentOfClass", assignmentOfClassRepository.customFindByIdAssignmentAndIdUser(idAssignment, idUser));
        model.addAttribute("assignmentOfUsers", assignmentsOfUserRepository.findByIdAssignmentAndIdClass(idAssignment, idClass, PageRequest.of(page - 1, 5)));
        return "teacherAssignmentCountPage";
    }
    
    @GetMapping("/detail/{id}")
    public String userAssignmentDetail(@PathVariable("id") Integer idAssignmentOfUser, Model model){
        AssignmentsOfUser aou = assignmentsOfUserRepository.findById(idAssignmentOfUser).get();
        
        model.addAttribute("assignmentReportData", assignmentReportRepository.findByIdAssignmentAndIdClassAndIdUser(aou.getIdAssignment(), aou.getIdClass(), aou.getIdUser()));
        model.addAttribute("userFullName", userRepository.findById(aou.getIdUser()).get().getFullName());
        model.addAttribute("assignmentOfUserData", aou);
        return "teacherAssignmentGradePage";
    }
    
    @GetMapping("/detail/submit/{id}")
    public String userAssignmentGrading(@PathVariable("id") Integer idAssignmentOfUser, @RequestParam("mark") Integer mark, Model model){
        AssignmentsOfUser aou = assignmentsOfUserRepository.findById(idAssignmentOfUser).get();
        
        if(assignmentReportRepository.findByIdAssignmentAndIdClassAndIdUser(aou.getIdAssignment(), aou.getIdClass(), aou.getIdUser()) == null){
            Report report = new Report();
            report.setIdAssignment(aou.getIdAssignment());
            report.setIdClass(aou.getIdClass());
            report.setIdUser(aou.getIdUser());
            report.setGrade(mark);
            assignmentReportRepository.save(report);
        }else{
            Report _report = assignmentReportRepository.findByIdAssignmentAndIdClassAndIdUser(aou.getIdAssignment(), aou.getIdClass(), aou.getIdUser());
            _report.setGrade(mark);
            assignmentReportRepository.save(_report);
        }
        
        return "redirect:/teacher/assignment/grade/detail/"+idAssignmentOfUser;
    }
}

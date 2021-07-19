/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.controller;

import com.mockproject.model.Assignment;
import com.mockproject.model.User;
import com.mockproject.repository.AssignmentRepository;
import com.mockproject.repository.SubjectAssignmentRepository;
import com.mockproject.repository.UserAssignmentCustomrepository;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author truon
 */
@Controller
public class AssignmentController {

    @Autowired
    AssignmentRepository assignmentRepository;

    @Autowired
    UserAssignmentCustomrepository userAssignmentCustomrepository;
    
    @Autowired
    SubjectAssignmentRepository subjectAssignmentRepository;

    @GetMapping("/adminListAssignment")
    public String assignmentListPage(Model model) {
        List<User> list = userAssignmentCustomrepository.findByStatus(true);
        Map<Integer, String> map = new HashMap<>();
        for(User u : list){
            map.put(u.getUser_id(), u.getFullName());
        }
        
        model.addAttribute("assignments", assignmentRepository.findAll());
        model.addAttribute("mapFullNames", map);
        return "adminListAssignment";
    }
    
    @GetMapping("/createAssignment")
    public String createAssignmentPage(Model model){
        model.addAttribute("assignment", new Assignment());
        model.addAttribute("subjects", subjectAssignmentRepository.findAll());
        System.out.println(subjectAssignmentRepository.findAll().size());
        return"adminCreateAssignment";
    }
    
    @PostMapping("/createAssignment/create")
    public String createAssignment(Assignment assignment){
        assignment.setCreateDate(new Date());
        assignmentRepository.save(assignment);
        return "redirect:/createAssignment";
    }

    @GetMapping("/editAssignment/{id}")
    public String editAssignment(@PathVariable("id") Integer id) {
        Optional<Assignment> assignmentData = assignmentRepository.findById(id);
        return "";
    }

    @GetMapping("/deleteAssignment/{id}")
    public String deleteAssignment(@PathVariable("id") Integer id) {
        Optional<Assignment> assignmentData = assignmentRepository.findById(id);

        if (assignmentData.isPresent()) {
            Assignment _assignment = assignmentData.get();
            boolean newStatus = !_assignment.isStatus();
            _assignment.setStatus(newStatus);
            assignmentRepository.save(_assignment);
        }
        return "redirect:/adminListAssignment";
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.controller;

import com.mockproject.model.Assignment;
import com.mockproject.model.AssignmentsOfClass;
import com.mockproject.model.User;
import com.mockproject.repository.AssignmentClassRepository;
import com.mockproject.repository.AssignmentOfClassRepository;
import com.mockproject.repository.AssignmentRepository;
import com.mockproject.repository.UserRepository;
import com.mockproject.security.CustomUserDetail;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author truon
 */
@Controller
@RequestMapping("/teacher/assignment")
public class AssignmentTeacherController {

    String subject;

    @Autowired
    AssignmentRepository assignmentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AssignmentClassRepository assignmentClassRepository;

    @Autowired
    AssignmentOfClassRepository assignmentOfClassRepository;

    @GetMapping("/{id}")
    public String assignmentListBySubjectId(@PathVariable("id") String idSubject, HttpSession session, Authentication authentication, Model model) {
        session.setAttribute("teacherSubjectAssignment", idSubject);
        Map<Integer, User> mapUser = userRepository.findAll().stream().collect(Collectors.toMap(User::getIdUser, user -> user));

        model.addAttribute("page", 1);
        model.addAttribute("mapUser", mapUser);
        model.addAttribute("assignments", assignmentRepository.findByIdSubject(idSubject, PageRequest.of(0, 5)));
        //model.addAttribute("assignments", assignmentRepository.findAllByIdSubjectAndIdUser(idSubject, ((CustomUserDetail) authentication.getPrincipal()).getUser().getIdUser(), PageRequest.of(0, 5)));

        return "teacherAssignmentListPage";

    }

    @GetMapping("/list")
    public String assignmentList(HttpSession session, Authentication authentication, Model model) {
        String idSubject = (String) session.getAttribute("teacherSubjectAssignment");
        Map<Integer, User> mapUser = userRepository.findAll().stream().collect(Collectors.toMap(User::getIdUser, user -> user));

        model.addAttribute("page", 1);
        model.addAttribute("mapUser", mapUser);
        model.addAttribute("assignments", assignmentRepository.findByIdSubject(idSubject, PageRequest.of(0, 5)));
        //model.addAttribute("assignments", assignmentRepository.findAllByIdSubjectAndIdUser(idSubject, ((CustomUserDetail) authentication.getPrincipal()).getUser().getIdUser(), PageRequest.of(0, 5)));
        return "teacherAssignmentListPage";

    }

    @GetMapping("/list/page/{page}")
    public String assignmentListPage(@PathVariable("page") Integer page, HttpSession session, Authentication authentication, Model model) {

        String idSubject = (String) session.getAttribute("teacherSubjectAssignment");
        Map<Integer, User> mapUser = userRepository.findAll().stream().collect(Collectors.toMap(User::getIdUser, user -> user));

        model.addAttribute("page", page);
        model.addAttribute("mapUser", mapUser);
        model.addAttribute("assignments", assignmentRepository.findByIdSubject(idSubject, PageRequest.of(page - 1 , 5)));
        //model.addAttribute("assignments", assignmentRepository.findAllByIdSubjectAndIdUser(idSubject, ((CustomUserDetail) authentication.getPrincipal()).getUser().getIdUser(), PageRequest.of(page - 1, 5)));
        return "teacherAssignmentListPage";
    }

    @GetMapping("/remove/{id}")
    public String removeAssignment(@PathVariable("id") Integer id, HttpSession session, @RequestParam("page") String page) {
        Optional<Assignment> assignmentData = assignmentRepository.findById(id);
        if (assignmentData.isPresent()) {
            Assignment _assignment = assignmentData.get();
            _assignment.setStatus(!_assignment.isStatus());
            assignmentRepository.save(_assignment);
        }
        return "redirect:/teacher/assignment/list/page/" + page;
    }

    @GetMapping("/create")
    public String createAssignment(@RequestParam(required = false) String success, Model model) {
        if(success != null){
            model.addAttribute("message", "Create success");
        }
        return "teacherAssignmentCreatePage";
    }

    @PostMapping("/create/save")
    public String createAssignmentSave(Assignment assignment, Model model) {
        assignment.setCreateDate(new Date());
        assignmentRepository.save(assignment);
        return "redirect:/teacher/assignment/create?success=1";
    }

    @GetMapping("/update/{id}")
    public String updateAssignment(@PathVariable("id") Integer id, @RequestParam(required = false) String success, Model model) {
        Optional<Assignment> assignmentData = assignmentRepository.findById(id);
        if (assignmentData.isPresent()) {
            model.addAttribute("assignmentData", assignmentData.get());
        }
        
        if(success != null){
            model.addAttribute("message", "Update success");
        }
        return "teacherAssignmentEditPage";
    }

    @PostMapping("/update/save")
    public String updateAssignmentSave(Assignment assignment, Model model) {
        Optional<Assignment> assignmentData = assignmentRepository.findById(assignment.getIdAssignment());
        if (assignmentData.isPresent()) {
            assignmentRepository.save(assignment);
        }
        return "redirect:/teacher/assignment/update/" + assignment.getIdAssignment() + "?success=1";
    }

    @GetMapping("/class/{id}")
    public String assignmentClass(@PathVariable("id") Integer idAssignment, HttpSession session, Model model) {
        String idSubject = (String) session.getAttribute("teacherSubjectAssignment");
        session.setAttribute("teacherCurrentIdAssignment", idAssignment);
        Map<Integer, Integer> map  = new HashMap<>();
        assignmentOfClassRepository.findByIdAssignment(idAssignment).forEach(s -> {
            map.put(s.getIdClass(), s.getIdAssignment());
        });

        model.addAttribute("page", 1);
        model.addAttribute("addedClassesMap", map);
        model.addAttribute("classes", assignmentClassRepository.findByIdSubjectAndStatus(idSubject, true, PageRequest.of(0, 5)));
        return "teacherAssignmentClassPage";
    }
    
    @GetMapping("/class/page/{id}")
    public String assignmentClassPage(@PathVariable("id") Integer page, HttpSession session, Model model){
        String idSubject = (String) session.getAttribute("teacherSubjectAssignment");
        Integer idAssignment = (Integer) session.getAttribute("teacherCurrentIdAssignment");
        Map<Integer, Integer> map  = new HashMap<>();
        assignmentOfClassRepository.findByIdAssignment(idAssignment).forEach(s -> {
            map.put(s.getIdClass(), s.getIdAssignment());
        });
        
        model.addAttribute("page", page);
        model.addAttribute("addedClassesMap", map);
        model.addAttribute("classes", assignmentClassRepository.findByIdSubjectAndStatus(idSubject, true, PageRequest.of(page - 1, 5)));
        return "teacherAssignmentClassPage";
    }
    

    @GetMapping("/class/add/{id}")
    public String assignmentClassAdd(@PathVariable("id") Integer idClass, @RequestParam("page") Integer page, HttpSession session, Model model) {
        Integer idAssignment = (Integer) session.getAttribute("teacherCurrentIdAssignment");
        if (assignmentOfClassRepository.findByIdAssignmentAndIdClass(idAssignment, idClass) == null) {
            AssignmentsOfClass aoc = new AssignmentsOfClass();
            aoc.setIdAssignment(idAssignment);
            aoc.setIdClass(idClass);
            assignmentOfClassRepository.save(aoc);
        }else{
            Integer idAssignmentOfClass = assignmentOfClassRepository.findByIdAssignmentAndIdClass(idAssignment, idClass).getIdAssignmentOfClass();
            assignmentOfClassRepository.deleteById(idAssignmentOfClass);
        }
        return "redirect:/teacher/assignment/class/page/" + page;
    }
}

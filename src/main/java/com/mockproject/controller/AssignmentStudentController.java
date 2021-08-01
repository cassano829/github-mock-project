/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.controller;

import com.mockproject.model.AssignmentsOfUser;
import com.mockproject.model.User;
import com.mockproject.repository.AssignmentRepository;
import com.mockproject.repository.AssignmentsOfUserRepository;
import com.mockproject.repository.UserRepository;
import com.mockproject.security.CustomUserDetail;
import java.util.Date;
import java.util.List;
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
@RequestMapping("/student/assignment/")
public class AssignmentStudentController {

    @Autowired
    AssignmentRepository assignmentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AssignmentsOfUserRepository assignmentsOfUserRepository;

    @GetMapping("/{id}")
    public String assignmentList(@PathVariable("id") Integer idClass, HttpSession session, Authentication authentication, Model model) {
        session.setAttribute("studentCurrentIdClass", idClass);
        User _user = ((CustomUserDetail) authentication.getPrincipal()).getUser();
        List<AssignmentsOfUser> list = assignmentsOfUserRepository.findByIdClassAndIdUser(idClass, _user.getIdUser());

        model.addAttribute("mapAssignment", list.stream().collect(Collectors.toMap(AssignmentsOfUser::getIdAssignment, user -> user.getIdAssignment())));
        model.addAttribute("mapUser", userRepository.findAll().stream().collect(Collectors.toMap(User::getIdUser, user -> user)));
        model.addAttribute("assignments", assignmentRepository.getListAssignmentOfClass(idClass, PageRequest.of(0, 4)));
        return "studentAssignmentListPage";
    }

    @GetMapping("page/{id}")
    public String assignmentListPage(@PathVariable("id") Integer page, HttpSession session, Authentication authentication, Model model) {
        Integer idClass = (Integer) session.getAttribute("studentCurrentIdClass");
        User _user = ((CustomUserDetail) authentication.getPrincipal()).getUser();
        List<AssignmentsOfUser> list = assignmentsOfUserRepository.findByIdClassAndIdUser(idClass, _user.getIdUser());

        model.addAttribute("mapAssignment", list.stream().collect(Collectors.toMap(AssignmentsOfUser::getIdAssignment, user -> user.getIdAssignment())));
        model.addAttribute("mapUser", userRepository.findAll().stream().collect(Collectors.toMap(User::getIdUser, user -> user)));
        model.addAttribute("assignments", assignmentRepository.getListAssignmentOfClass(idClass, PageRequest.of(page - 1, 4)));
        return "studentAssignmentListPage";
    }

    @GetMapping("create/{id}")
    public String assignmentCreateSubmition(@PathVariable("id") Integer idAssignment, Authentication authentication, HttpSession session, @RequestParam(required = false) String success, Model model) {
        if (success != null) {
            model.addAttribute("message", "Create success");
        }

        model.addAttribute("assignmentData", assignmentRepository.findById(idAssignment).get());
        return "studentAssignmentCreateSubmissionPage";
    }

    @PostMapping("create/submit")
    public String assignmentCreateSubmitionSubmit(AssignmentsOfUser assignmentsOfUser) {
        assignmentsOfUser.setUploadTime(new Date());
        assignmentsOfUserRepository.save(assignmentsOfUser);
        return "redirect:/student/assignment/create/" + assignmentsOfUser.getIdAssignment() + "?success=1";
    }

    @GetMapping("update/{id}")
    public String assignmentUpdateSubmition(@PathVariable("id") Integer idAssignment, Authentication authentication, HttpSession session, @RequestParam(required = false) String success, Model model) {
        User _user = ((CustomUserDetail) authentication.getPrincipal()).getUser();
        Integer idClass = (Integer) session.getAttribute("studentCurrentIdClass");

        if (success != null) {
            model.addAttribute("message", "Update success");
        }

        model.addAttribute("viewOnly", false);
        model.addAttribute("assignmentOfUserData", assignmentsOfUserRepository.findByIdAssignmentAndIdClassAndIdUser(idAssignment, idClass, _user.getIdUser()));
        model.addAttribute("assignmentData", assignmentRepository.findById(idAssignment).get());
        return "studentAssignmentUpdateSubmissionPage";
    }

    @PostMapping("update/submit")
    public String assignmentUpdateSubmitionSubmit(AssignmentsOfUser assignmentsOfUser) {
        AssignmentsOfUser aou = assignmentsOfUserRepository.findByIdAssignmentAndIdClassAndIdUser(assignmentsOfUser.getIdAssignment(), assignmentsOfUser.getIdClass(), assignmentsOfUser.getIdUser());
        Optional<AssignmentsOfUser> assignmentsOfUserData = assignmentsOfUserRepository.findById(aou.getIdAssignmentOfUser());
        if (assignmentsOfUserData.isPresent()) {
            AssignmentsOfUser _assignmentsOfUser = assignmentsOfUserData.get();
            _assignmentsOfUser.setContent(assignmentsOfUser.getContent());
            _assignmentsOfUser.setUploadTime(new Date());
            _assignmentsOfUser.setAttachments(assignmentsOfUser.getAttachments());
            assignmentsOfUserRepository.save(_assignmentsOfUser);
        }
        return "redirect:/student/assignment/update/" + assignmentsOfUser.getIdAssignment() + "?success=1";
    }

    @GetMapping("view/{id}")
    public String assignmentViewSubmition(@PathVariable("id") Integer idAssignment, Authentication authentication, HttpSession session, Model model) {
        User _user = ((CustomUserDetail) authentication.getPrincipal()).getUser();
        Integer idClass = (Integer) session.getAttribute("studentCurrentIdClass");

        model.addAttribute("viewOnly", true);
        model.addAttribute("assignmentOfUserData", assignmentsOfUserRepository.findByIdAssignmentAndIdClassAndIdUser(idAssignment, idClass, _user.getIdUser()));
        model.addAttribute("assignmentData", assignmentRepository.findById(idAssignment).get());
        return "studentAssignmentUpdateSubmissionPage";
    }
}

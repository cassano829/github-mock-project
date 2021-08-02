package com.mockproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.mockproject.security.CustomUserDetail;
import com.mockproject.service.SubjectService;


@Controller
public class SubjectController {
	
	@Autowired
	SubjectService subjectService ;
	
	@GetMapping("/studen1t")
	public String listAllSubject(Model model) {
		CustomUserDetail user = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		subjectService.findSubjectByIdUser(user.getIdUser());
		return "home";
	}
}

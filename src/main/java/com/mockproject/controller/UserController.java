/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.controller;

import com.mockproject.model.Answer;
import com.mockproject.model.AssignmentOfClass;
import com.mockproject.model.Assignment;
import com.mockproject.model.Question;
import com.mockproject.model.Quiz;
import com.mockproject.model.QuizDetail;
import com.mockproject.model.QuizOfClass;
import com.mockproject.model.QuizOfUser;
import com.mockproject.model.Report;
import com.mockproject.model.User;
import com.mockproject.model.UserOfClass;
import com.mockproject.security.CustomUserDetail;
import com.mockproject.service.AnswerService;
import com.mockproject.service.AssignmentOfClassService;
import com.mockproject.service.AssignmentService;
import com.mockproject.service.QuestionService;
import com.mockproject.service.QuizDetailService;
import com.mockproject.service.QuizOfClassService;
import com.mockproject.service.QuizOfUserService;
import com.mockproject.service.QuizService;
import com.mockproject.service.UserOfClassService;
import com.mockproject.service.UserService;
import com.mockproject.model.Class;
import com.mockproject.service.ReportService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author ACER
 */
@Controller
public class UserController{ 
    @Autowired
   	QuizOfClassService quizOfClassService ;
    
    @Autowired
   	QuizService quizService ;
    
    @Autowired
   	QuestionService questionService ;
    
    @Autowired
   	AnswerService answerService ;
    
    @Autowired
   	QuizOfUserService quizOfUserService ;
    
    @Autowired
   	QuizDetailService quizDetailService ;
    
    @Autowired
   	AssignmentService assignmentService ;
    
    @Autowired
   	AssignmentOfClassService assignmentOfClassService ;
    
    @Autowired
   	ReportService reportService ;
    
    @Autowired
   	UserService userService ;
    
    @Autowired
   	UserOfClassService userOfClassService ;
    
    @GetMapping("/teacher-assignment-report")
    public String teacherAsignmentReportPage(HttpSession session,Model model,@RequestParam(name = "idAssignment") int idAssignment) {
    	List<AssignmentOfClass> listAsignmentOfClass = new ArrayList<AssignmentOfClass>();
    	List<Report> listReport = new ArrayList<Report>();
    	LinkedHashMap<User, Report> userReport = new LinkedHashMap<User, Report>();
    	
    	listAsignmentOfClass =(List<AssignmentOfClass>)session.getAttribute("listAsignmentOfClass");
    	listReport = reportService.findReportByIdAssignmentAndIdClass(idAssignment, listAsignmentOfClass.get(0).getIdClass());
    	
    	for(Report report:listReport) {
    		userReport.put(userService.findUserByidUser(report.getIdUser()), report);
    	}
    	
    	model.addAttribute("userReports", userReport);
        return "/teacher-assignment-report";
    }

    @GetMapping("/teacher-quiz-report")
    public String teacherQuizReportPage(HttpSession session,Model model,@RequestParam(name = "idQuiz") int idQuiz) {
    	List<QuizOfClass> listQuizOfClass = new ArrayList<QuizOfClass>();
    	List<Report> listReport = new ArrayList<Report>();
    	List<User> listUser = new ArrayList<User>();
    	TreeMap<User, List<Report>> userReport = new TreeMap<User, List<Report>>();
    	
    	listQuizOfClass =(List<QuizOfClass>)session.getAttribute("listQuizOfClass");
    	listReport = reportService.findDistinctByIdQuizAndIdClass(idQuiz, listQuizOfClass.get(0).getIdClass());
    	
    	for(Report report:listReport) {
    		listUser.add(userService.findUserByidUser(report.getIdUser()));
    	}
    	
    	for(User user:listUser) {
    		userReport.put(user, reportService.findReportByIdQuizAndIdClassAndIdUser(idQuiz, listQuizOfClass.get(0).getIdClass(), user.getIdUser()));
    	}
    	model.addAttribute("userReports", userReport);
        return "/teacher-quiz-report";
    }
    
    @GetMapping("/teacher-class-report")
    public String teacherClassReportPage(HttpSession session,@RequestParam(name = "idClass") int idClass,Model model) {
    	ArrayList<String> listTitleQuizAndAssignment = new ArrayList<String>();
    	List<AssignmentOfClass> listAssignmentOfClass = new ArrayList<AssignmentOfClass>();
    	List<QuizOfClass> listQuizOfClass = new ArrayList<QuizOfClass>();
    	List<UserOfClass> listUserOfClass = new ArrayList<UserOfClass>();
    	List<User> listUser = new ArrayList<User>();
    	LinkedHashMap<User, ArrayList<Float>> infoStudent = new LinkedHashMap<User, ArrayList<Float>>();
    	Assignment assignment = new Assignment();
    	
    	listAssignmentOfClass = assignmentOfClassService.findAssignmentOfClassByIdClass(idClass);
    	listQuizOfClass = quizOfClassService.findQuizOfClassByIdClass(idClass);
    	
    	
    	//add title
    	listTitleQuizAndAssignment.add("StudentID");
    	listTitleQuizAndAssignment.add("Student Name");
    	for(AssignmentOfClass assignmentOfClass:listAssignmentOfClass) {
    		assignment = assignmentService.findAssignmentByidAssignment(assignmentOfClass.getIdAssignment());
    		listTitleQuizAndAssignment.add(assignment.getTitle());
    		
    	}
    	for(QuizOfClass quizOfClass:listQuizOfClass) {
    		Quiz quiz = quizService.findQuizByIdQuiz(quizOfClass.getIdQuiz());
    		listTitleQuizAndAssignment.add(quiz.getName());
    	}
    	
    	//tim tat ca user trong class
    	listUserOfClass = userOfClassService.findUserOfClassByIdClass(idClass);
    	for(UserOfClass userOfClass:listUserOfClass) {
    		listUser.add(userService.findUserByidUser(userOfClass.getIdUser())) ;
    	}
    	
    	
    	for(User user:listUser) {
    		ArrayList<Float> listGrade = new ArrayList<Float>();
    		//lay diem Assignment cua user
    		for(AssignmentOfClass assignmentOfClass:listAssignmentOfClass) {
        		assignment = assignmentService.findAssignmentByidAssignment(assignmentOfClass.getIdAssignment());
        		Report report = reportService.findReportByIdAssignmentAndIdClassAndIdUser(assignment.getIdAssignment(),idClass,user.getIdUser());
        		if(report!=null) {
        			listGrade.add(report.getGrade());
        		}else {
					listGrade.add(null);
				}
        	}
    		
    		//lay diem quiz cua User
    		for(QuizOfClass quizOfClass:listQuizOfClass) {
        		Quiz quiz = quizService.findQuizByIdQuiz(quizOfClass.getIdQuiz());
        		String grade = reportService.findMaxQuizGrade(user.getIdUser(),idClass,quiz.getIdQuiz());
        		if(grade!=null) {
        			listGrade.add(Float.parseFloat(grade));
        		}else {
					listGrade.add(null);
				}
        	}
    		
    		infoStudent.put(user, listGrade);
    	}
    	model.addAttribute("infoStudent", infoStudent);
    	model.addAttribute("titles", listTitleQuizAndAssignment);
    	
        return "/teacher-class-report";
    }
    

    
    @GetMapping("/student-mark-report")
    public String HistoryProcess(Model model,HttpSession session) {
    	TreeMap<Quiz, List<QuizOfUser>> tree = new TreeMap<Quiz, List<QuizOfUser>>();
    	CustomUserDetail user = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	List<Quiz> listQuizes = new ArrayList<>() ; 
    	List<QuizOfClass> listQuizOfClass = new ArrayList<>() ;
    	Class classes = (Class)session.getAttribute("class");
    	listQuizOfClass = quizOfClassService.findQuizOfClassByIdClass(classes.getIdClass());
    	
    	//tim tat ca quiz cua lop
    	for(QuizOfClass quizOfClass: listQuizOfClass) {
    		listQuizes.add(quizService.findQuizByIdQuiz(quizOfClass.getIdQuiz()));
    	}

    	for(Quiz quiz: listQuizes) {
    		List<QuizOfUser> listQuizOfUser =  quizOfUserService.findQuizOfUserByIdUserAndIdQuiz(user.getIdUser(), quiz.getIdQuiz());
    		if(listQuizOfUser.size()!=0) {
    			tree.put(quiz, listQuizOfUser);
    		}
    		
    	}
    	session.setAttribute("quizInfoes", tree);
        return "student-mark-report";
    }
    
    @GetMapping("/student-quiz-review")
    public String feedBackPage(Model model,@RequestParam(value="idQuizOfUser",required = false) int idQuizOfUser) {
    	List<QuizDetail> listQuizDetail = new ArrayList<QuizDetail>();
    	List<Question> listQuestion = new ArrayList<Question>();
    	LinkedHashMap<Question, List<Answer>> ht = new LinkedHashMap<Question, List<Answer>>();
    	listQuizDetail = quizDetailService.findQuizDetailByQuizOfUser_idQuizOfUser(idQuizOfUser);
    	
    	for(QuizDetail quizDetail: listQuizDetail) {
    		listQuestion = questionService.findQuestionByidQuestion(quizDetail.getIdQuestion());
    		for(Question question:listQuestion) {
    				ht.put(question, answerService.findAnswerByQuestion_idQuestion(question.getIdQuestion()));
    		}    		
    	}
    	model.addAttribute("listQuizDetails", listQuizDetail);
    	model.addAttribute("feedbacks", ht);
        return "/student-quiz-review";
    }
    
}

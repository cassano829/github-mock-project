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
import com.mockproject.model.Subject;
import com.mockproject.model.User;
import com.mockproject.model.UserOfClass;
import com.mockproject.model.User_Role;
import com.mockproject.security.CustomUserDetail;
import com.mockproject.security.UserDetailServiceImp;
import com.mockproject.service.AnswerService;
import com.mockproject.service.AssignmentOfClassService;
import com.mockproject.service.AssignmentService;
import com.mockproject.service.ClassService;
import com.mockproject.service.QuestionService;
import com.mockproject.service.QuizDetailService;
import com.mockproject.service.QuizOfClassService;
import com.mockproject.service.QuizOfUserService;
import com.mockproject.service.QuizService;
import com.mockproject.service.SubjectService;
import com.mockproject.service.UserOfClassService;
import com.mockproject.service.UserService;
import com.mockproject.service.User_RoleService;
import net.bytebuddy.utility.RandomString;
import com.mockproject.model.Class;
import com.mockproject.service.ReportService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author ACER
 */
@Controller
public class UserController {

    @Autowired
    UserDetailServiceImp service;

    @Autowired
    User_RoleService user_roleService;
    
    @Autowired
	SubjectService subjectService ;
    
    @Autowired
   	ClassService classService ;
    
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
    

    @GetMapping("/")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/403")
    public String handleError() {
        return "403";
    }

    //admin
    @GetMapping("/admin/home")
    public String adminHomePage() {
        return "adminHome";
    }

    @GetMapping("/admin/subject")
    public String adminSubjectPage() {
        return "adminSubject";
    }

    @GetMapping("/admin/account")
    public String adminAccountPage() {
        return "adminAccount";
    }

    @GetMapping("/admin/user")
    public String adminUserPage() {
        return "adminUser";
    }

    //teacher
    @GetMapping("/teacher/home")
    public String teacherHomePage() {
        return "teacherHome";
    }

    @GetMapping("/teacher/subject")
    public String teacherSubjectPage(HttpSession session) {
    	CustomUserDetail user = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		session.setAttribute("subjects", subjectService.findSubjectByIdUser(user.getIdUser()));
        return "teacher-subject";
    }
    
    @GetMapping("/teacher-class")
    public String teacherClassPage(HttpSession session,@RequestParam(name = "idSubject") int idSubject) {
    	CustomUserDetail user = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		session.setAttribute("classes", classService.findClassByUser_idUserAndSubject_idSubjectAndStatus(user.getIdUser(),idSubject,true));
        return "/teacher-class";
    }
    
    @GetMapping("/teacher-assignment")
    public String teacherAsignmentPage(HttpSession session,@RequestParam(name = "idClass") int idClass) {
    	List<AssignmentOfClass> listAsignmentOfClass = new ArrayList<AssignmentOfClass>();
    	List<Assignment> listAssignment = new ArrayList<Assignment>();
    	listAsignmentOfClass = assignmentOfClassService.findAssignmentOfClassByClass_idClass(idClass);
    	for(AssignmentOfClass asignmentOfClass:listAsignmentOfClass) {
    		listAssignment.add(assignmentService.findAssignmentByidAssignment(asignmentOfClass.getAssignment().getIdAssignment()));
    	}
    	session.setAttribute("listAsignmentOfClass", listAsignmentOfClass); 
    	session.setAttribute("listAssignment", listAssignment);    	
        return "/teacher-assignment";
    }
    
    @GetMapping("/teacher-assignment-report")
    public String teacherAsignmentReportPage(HttpSession session,Model model,@RequestParam(name = "idAssignment") int idAssignment) {
    	List<AssignmentOfClass> listAsignmentOfClass = new ArrayList<AssignmentOfClass>();
    	List<Report> listReport = new ArrayList<Report>();
    	LinkedHashMap<User, Report> userReport = new LinkedHashMap<User, Report>();
    	
    	listAsignmentOfClass =(List<AssignmentOfClass>)session.getAttribute("listAsignmentOfClass");
    	listReport = reportService.findReportByAssignment_idAssignmentAndClas_idClass(idAssignment, listAsignmentOfClass.get(0).getClas().getIdClass());
    	
    	for(Report report:listReport) {
    		userReport.put(userService.findUserByidUser(report.getUser().getIdUser()), report);
    	}
    	
    	model.addAttribute("userReports", userReport);
        return "/teacher-assignment-report";
    }
    
    @GetMapping("/teacher-quiz")
    public String teacherQuizPage(HttpSession session,@RequestParam(name = "idClass") int idClass) {
        List<Quiz> listQuiz = new ArrayList<>() ; 
    	List<QuizOfClass> listQuizOfClass = new ArrayList<>() ;
    	
    	listQuizOfClass = quizOfClassService.findQuizOfClassByIdClass(idClass);
    	for(int i =0; i<listQuizOfClass.size();i++) {
    		listQuiz.add(quizService.findQuizByIdQuiz(listQuizOfClass.get(i).getQuiz().getIdQuiz()));
    	}
    	session.setAttribute("listQuizOfClass",listQuizOfClass);
    	session.setAttribute("quizes",listQuiz);
        return "/teacher-quiz";
    }
    
    @GetMapping("/teacher-quiz-report")
    public String teacherQuizReportPage(HttpSession session,Model model,@RequestParam(name = "idQuiz") int idQuiz) {
    	List<QuizOfClass> listQuizOfClass = new ArrayList<QuizOfClass>();
    	List<Report> listReport = new ArrayList<Report>();
    	List<User> listUser = new ArrayList<User>();
    	TreeMap<User, List<Report>> userReport = new TreeMap<User, List<Report>>();
    	
    	listQuizOfClass =(List<QuizOfClass>)session.getAttribute("listQuizOfClass");
    	listReport = reportService.findDistinctByQuiz_idQuizAndClas_idClass(idQuiz, listQuizOfClass.get(0).getClas().getIdClass());
    	
    	for(Report report:listReport) {
    		listUser.add(userService.findUserByidUser(report.getUser().getIdUser()));
    	}
    	
    	for(User user:listUser) {
    		userReport.put(user, reportService.findReportByQuiz_idQuizAndClas_idClassAndUser_idUser(idQuiz, listQuizOfClass.get(0).getClas().getIdClass(), user.getIdUser()));
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
    	
    	listAssignmentOfClass = assignmentOfClassService.findAssignmentOfClassByClass_idClass(idClass);
    	listQuizOfClass = quizOfClassService.findQuizOfClassByIdClass(idClass);
    	
    	
    	//add title
    	listTitleQuizAndAssignment.add("StudentID");
    	listTitleQuizAndAssignment.add("Student Name");
    	for(AssignmentOfClass assignmentOfClass:listAssignmentOfClass) {
    		assignment = assignmentService.findAssignmentByidAssignment(assignmentOfClass.getAssignment().getIdAssignment());
    		listTitleQuizAndAssignment.add(assignment.getTitle());
    		
    	}
    	for(QuizOfClass quizOfClass:listQuizOfClass) {
    		Quiz quiz = quizService.findQuizByIdQuiz(quizOfClass.getQuiz().getIdQuiz());
    		listTitleQuizAndAssignment.add(quiz.getName());
    	}
    	
    	//tim tat ca user trong class
    	listUserOfClass = userOfClassService.findUserOfClassByClas_idClass(idClass);
    	for(UserOfClass userOfClass:listUserOfClass) {
    		listUser.add(userService.findUserByidUser(userOfClass.getUser().getIdUser())) ;
    	}
    	
    	
    	for(User user:listUser) {
    		ArrayList<Float> listGrade = new ArrayList<Float>();
    		
    		//lay diem Assignment cua user
    		for(AssignmentOfClass assignmentOfClass:listAssignmentOfClass) {
        		assignment = assignmentService.findAssignmentByidAssignment(assignmentOfClass.getAssignment().getIdAssignment());
        		Report report = reportService.findReportByAssignment_idAssignmentAndClas_idClassAndUser_idUser(assignment.getIdAssignment(),idClass,user.getIdUser());
        		if(report!=null) {
        			listGrade.add(report.getGrade());
        		}else {
					listGrade.add(null);
				}
        	}
    		
    		//lay diem quiz cua User
    		for(QuizOfClass quizOfClass:listQuizOfClass) {
        		Quiz quiz = quizService.findQuizByIdQuiz(quizOfClass.getQuiz().getIdQuiz());
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
    

    @GetMapping("/teacher/account")
    public String teacherAccountPage() {
        return "teacherAccount";
    }

    //student
    @GetMapping("/student/home")
    public String studentHomePage() {
        return "student-home";
    }

    @GetMapping("/student/class")
    public String studentClassPage(HttpSession session) {
    	CustomUserDetail user = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		session.setAttribute("classes", classService.findClassByIdUser(user.getIdUser(),true));
        return "student-class";
    }
    
    @GetMapping("/student/class/entered")
    public String studentClassEnteredPage(Model model,HttpSession session,@RequestParam(name = "idClass") int idClass) {
    	session.setAttribute("class", classService.findClassById(idClass));
        return "student-class-entered";
    }
    
    @GetMapping("/student-quiz-list")
    public String studentListQuizPage(Model model,HttpSession session) {
    	List<Quiz> listQuiz = new ArrayList<>() ; 
    	List<QuizOfClass> listQuizOfClass = new ArrayList<>() ;
    	Class classes = (Class)session.getAttribute("class");
    	listQuizOfClass = quizOfClassService.findQuizOfClassByIdClass(classes.getIdClass());
    	for(int i =0; i<listQuizOfClass.size();i++) {
    		listQuiz.add(quizService.findQuizByIdQuiz(listQuizOfClass.get(i).getQuiz().getIdQuiz()));
    	}
    	
    	session.setAttribute("quizes",listQuiz);
        return "student-quiz-list";
    }
    
    @GetMapping("/quiz")
    public String studentQuizPage(Model model,@RequestParam(value="idQuiz",required = false) int idQuiz) {
    	List<Question> listQuestion = new ArrayList<>() ;
    	Hashtable<Question,List<Answer> > hashTable = new Hashtable<Question, List<Answer>>();
    	listQuestion = questionService.findQuestionByQuiz_idQuizAndStatus(idQuiz, true);
    	
    	for(int i= 0;i<listQuestion.size();i++) {
    		hashTable.put(listQuestion.get(i), answerService.findAnswerByQuestion_idQuestion(listQuestion.get(i).getIdQuestion()));
    	}
    	model.addAttribute("idQuiz", idQuiz);
    	model.addAttribute("questionsAndAnswers", hashTable);
        return "quiz";
    }
    
    @PostMapping("/student/quiz/entered")
    public String studentHistoryPage(Model model, HttpServletRequest req) {
    	CustomUserDetail user = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	User userr = new User();
    	Quiz quiz = new Quiz();
    	String[] idQuestions = req.getParameterValues("idQuestion");
    	
    	//add vao quiz of user
    	String idQuiz =  req.getParameter("idQuiz");
		userr.setIdUser(user.getIdUser());
		quiz.setIdQuiz(Integer.parseInt(idQuiz));
		QuizOfUser quizOfUser = new QuizOfUser(false, 0, userr, quiz);
		quizOfUserService.saveQuizOfUser(quizOfUser);
    	
    	
		//lay quizOfUser cuoi cung trong mang
		List<QuizOfUser> listQuizOfUser = new ArrayList(); 
		listQuizOfUser = quizOfUserService.findQuizOfUserByUser_idUserAndQuiz_idQuiz(user.getIdUser(), quiz.getIdQuiz());
		quizOfUser = listQuizOfUser.get(listQuizOfUser.size()-1);
		
		//save vao quizDetail
    	for(String idQuestion: idQuestions) {
    		Question question = new Question();
    		question.setIdQuestion(Integer.parseInt(idQuestion));
    		String answer = req.getParameter(idQuestion);
    		quizDetailService.saveQuizDetail(new QuizDetail(answer,question , quizOfUser));
    		
    	} 	
    	
    	List<QuizDetail> listQuizDetail = new ArrayList<QuizDetail>();
    	listQuizDetail = quizDetailService.findQuizDetailByQuizOfUser_idQuizOfUser(quizOfUser.getIdQuizOfUser());
    	
    	//check so cau dung va grade	
    	int totalCorrect = 0;
    	List<Answer> listAnswer = new ArrayList();
    	listAnswer = answerService.findAllAnswer();
    	for(QuizDetail quizDetail: listQuizDetail) {
    		for(Answer answer: listAnswer) {
    			if(quizDetail.getUserAnswer()!=null) {
    				if(quizDetail.getQuestion().getIdQuestion() == answer.getQuestion().getIdQuestion()) {
            			if(quizDetail.getUserAnswer().equals(answer.getContent()) && answer.isCorrect() ) {
            				totalCorrect++;
            			}
            		}
    			}
    			
    		}	
    	}
    	
    	float totalGrade = (float)Math.round(((totalCorrect*1.0/listQuizDetail.size())*10)*10)/10;
    	
    	
    	//dung tren 50% = pass
    	if(totalCorrect >= (float)listQuizDetail.size()/2) {
    		quizOfUserService.updateQuizesOfUserByidQuizOfUser(totalCorrect, true, quizOfUser.getIdQuizOfUser(),totalGrade);
    	}else {
    		quizOfUserService.updateQuizesOfUserByidQuizOfUser(totalCorrect, false, quizOfUser.getIdQuizOfUser(),totalGrade);
    	}
        return "redirect:/student-mark-report";
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
    		listQuizes.add(quizService.findQuizByIdQuiz(quizOfClass.getQuiz().getIdQuiz()));
    	}

    	for(Quiz quiz: listQuizes) {
    		List<QuizOfUser> listQuizOfUser =  quizOfUserService.findQuizOfUserByUser_idUserAndQuiz_idQuiz(user.getIdUser(), quiz.getIdQuiz());
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
    		listQuestion = questionService.findQuestionByidQuestion(quizDetail.getQuestion().getIdQuestion());
    		for(Question question:listQuestion) {
    				ht.put(question, answerService.findAnswerByQuestion_idQuestion(question.getIdQuestion()));
    		}    		
    	}
    	model.addAttribute("listQuizDetails", listQuizDetail);
    	model.addAttribute("feedbacks", ht);
        return "/student-quiz-review";
    }
    
    @GetMapping("/student/class/entered/editAsignment")
    public String studentEditAsignment(Model model) {
        return "studentEditAssignment";
    }

    @GetMapping("/student/account")
    public String studentAccountPage() {
        return "student-account";
    }

    @GetMapping("/student/create")
    public String signUp(Model model) {
        model.addAttribute("user", new User());
        return "sign-up";
    }

    @PostMapping("/student/save")
    public String signUp(@ModelAttribute("user") User user,
            Model model, @RequestParam("rePassword") String rePwd) {

        boolean checkEmpty = true;
        if (user.getEmail().trim().isEmpty()) {
            checkEmpty = false;
        }
        if (user.getFullName().trim().isEmpty()) {
            checkEmpty = false;
        }
        if (user.getPassword().trim().isEmpty()) {
            checkEmpty = false;
        }
        if (checkEmpty) {
            String error = "";
            boolean checkCorrect = true;
            boolean checkEmail = service.isValidEmail(user.getEmail());
            boolean checkRePwd = rePwd.matches(user.getPassword());

            if (!checkEmail) {
                checkCorrect = false;
                error = "Wrong email format! (ex: alpha123@gmail.com)";
            }
            if (!checkRePwd) {
                checkCorrect = false;
                error = "Re-password not matches!";
            }
            if (checkCorrect) {
                boolean checkExist = service.isEmailUnique(user.getEmail());
                if (!checkExist) {
                    model.addAttribute("messageError", "Email has already existed!");
                    return "sign-up";
                } else {
                    service.register(user);
                    User_Role ur = new User_Role();
                    ur.setIdRole(3); // 3 = student role
                    ur.setIdUser(user.getIdUser());
                    user_roleService.save(ur);
                    model.addAttribute("message", "Registered successfully!");
                    model.addAttribute("nofitication", "Please check your email to verify your account!");
                    return "verify";
                }
            } else {
                model.addAttribute("messageError", error);
                return "sign-up";
            }
        } else {
            model.addAttribute("messageError", "Cannot empty any fields");
            return "sign-up";
        }
    }

    @PostMapping("/student/verify")
    public String verifyAccount(@RequestParam("AUTHEN_CODE") String code, Model model) {
        boolean verified = service.verify(code);
        if (verified) {
            return "verify_success";
        } else {
            model.addAttribute("messageError", "Invalid code verify!");
            return "verify";
        }
    }
}

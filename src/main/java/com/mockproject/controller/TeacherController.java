/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.controller;

import com.mockproject.model.Answer;
import com.mockproject.model.Assignment;
import com.mockproject.model.AssignmentsOfClass;
import com.mockproject.model.AssignmentsOfUser;
import com.mockproject.model.User;
import com.mockproject.security.UserDetailServiceImp;
import com.mockproject.service.ClassService;
import com.mockproject.security.CustomUserDetail;
import java.util.HashMap;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.mockproject.model.Quiz;
import com.mockproject.service.QuizService;
import com.mockproject.service.SubjectService;
import org.springframework.web.bind.annotation.PathVariable;
import com.mockproject.model.Class;
import com.mockproject.model.Question;
import com.mockproject.model.QuizOfClass;
import com.mockproject.model.QuizOfUser;
import com.mockproject.model.UserOfClass;
import com.mockproject.service.AssignmentOfClassService;
import com.mockproject.service.AssignmentOfUserService;
import com.mockproject.service.AssignmentService;
import com.mockproject.service.QuestionService;
import com.mockproject.service.QuizOfClassService;
import com.mockproject.service.QuizOfUserService;
import com.mockproject.service.UserOfClassService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    public final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

    @Autowired
    QuizOfUserService quizOfUserService;
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Autowired
    UserController userController;

    @Autowired
    ClassService classService;

    @Autowired
    SubjectService subjectService;

    @Autowired
    QuizService quizService;

    @Autowired
    QuestionService questionService;

    @Autowired
    UserDetailServiceImp userService;

    @Autowired
    UserDetailServiceImp service;

    @Autowired
    AssignmentService assignmentService;

    @Autowired
    AssignmentOfClassService assignmentOfClassService;

    @Autowired
    AssignmentOfUserService assignmentOfUserService;

    @Autowired
    QuizOfClassService quizOfClassService;

    @Autowired
    UserOfClassService userOfClassService;

    @GetMapping("/quiz/viewStudentsOfQuiz")
    public String viewListStudentOfQuiz(HttpServletRequest request, Model model, HttpSession session) {
        int idQuiz = Integer.parseInt(request.getParameter("idQuiz"));
        int idClass = (int) session.getAttribute("idClass");
        List<User> usersOfClass = userService.findAllByidClass(idClass);
        Map<User, List<QuizOfUser>> mapQuizOfStudent = new HashMap<>();
        for (User user : usersOfClass) {
            List<QuizOfUser> quizOfStudents = quizOfUserService.findQuizOfUserByIdUserAndIdQuiz(user.getIdUser(), idQuiz);
            mapQuizOfStudent.put(user, quizOfStudents);
        }
        model.addAttribute("mapQuizOfStudent", mapQuizOfStudent);
        model.addAttribute("nameQuiz", quizService.getQuizByIdQuiz(idQuiz).getNameQuiz());
        return "teacher-quiz-report";
    }

    @GetMapping("/quiz/showQuizesOfTeacher/{idSubject}/{page}")
    public String showQuizesofTeacher(HttpServletRequest request, Model model, HttpSession session, @PathVariable("idSubject") String idSubject, @PathVariable("page") int page) {
        CustomUserDetail userDetail = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String search = "";
        if (request.getParameter("txtSearch") != null) {
            search = request.getParameter("txtSearch");
        }
        Page<Quiz> quizs = quizService.getAllQuizByIdSubjectAndIdTeacherAndNameQuiz(search, idSubject, userDetail.getUser().getIdUser(), PageRequest.of(page - 1, 4));
        model.addAttribute("search", search);
        model.addAttribute("quizes", quizs);
        model.addAttribute("page", page);
        session.setAttribute("idSubject", idSubject);
        return "teacherQuizCommon";
    }

    @GetMapping("/quiz/addClassToQuiz/{page}")
    public String showAllClassOfTeacher(@PathVariable("page") int page, Model model, HttpSession session, HttpServletRequest request) {
        String idSubject = (String) session.getAttribute("idSubject");
        int idQuiz = Integer.parseInt(request.getParameter("idQuiz"));
        CustomUserDetail userDetail = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Integer> addedClasses = quizOfClassService.findAllByIdQuiz(idQuiz);
        model.addAttribute("classes", classService.getListClassByIdTeacherAndIdSubject(userDetail.getUser().getIdUser(), idSubject, PageRequest.of(page - 1, 4)));
        model.addAttribute("addedClasses", addedClasses);
        model.addAttribute("page", page);
        model.addAttribute("idQuiz", idQuiz);
        return "teacherAddQuizToClass";
    }

    @GetMapping("/quiz/addClassToQuiz/{page}/add")
    public String addQuizToClass(@PathVariable("page") int page, HttpServletRequest request, Model model) {
        int idClass = Integer.parseInt(request.getParameter("idClass"));
        int idQuiz = Integer.parseInt(request.getParameter("idQuiz"));
        QuizOfClass quizOfClass = new QuizOfClass();
        quizOfClass.setIdClass(idClass);
        quizOfClass.setIdQuiz(idQuiz);
        quizOfClassService.save(quizOfClass);
        model.addAttribute("page", page);
        return "redirect:/teacher/quiz/addClassToQuiz/" + page + "?idQuiz=" + idQuiz;
    }

    @GetMapping("/quiz/addClassToQuiz/{page}/undo")
    public String undoAddQuizToClass(@PathVariable("page") int page, HttpServletRequest request, Model model) {
        int idClass = Integer.parseInt(request.getParameter("idClass"));
        int idQuiz = Integer.parseInt(request.getParameter("idQuiz"));
        QuizOfClass quizOfClass = quizOfClassService.findByIdQuizAndIdClass(idQuiz, idClass);
        quizOfClassService.delete(quizOfClass);
        model.addAttribute("page", page);
        return "redirect:/teacher/quiz/addClassToQuiz/" + page + "?idQuiz=" + idQuiz;
    }

    @GetMapping("/classReport/{idClass}")
    public String teacherClassReportPage(HttpSession session, @PathVariable(name = "idClass") int idClass, Model model) {
        ArrayList<String> listTitleQuizAndAssignment = new ArrayList<String>();
        List<AssignmentsOfClass> listAssignmentOfClass = new ArrayList<AssignmentsOfClass>();
        List<QuizOfClass> listQuizOfClass = new ArrayList<QuizOfClass>();
        List<UserOfClass> listUserOfClass = new ArrayList<UserOfClass>();
        List<User> listUser = new ArrayList<User>();
        LinkedHashMap<User, ArrayList<Double>> infoStudent = new LinkedHashMap<User, ArrayList<Double>>();
        Assignment assignment = new Assignment();

        listAssignmentOfClass = assignmentOfClassService.findAssignmentOfClassByIdClass(idClass);
        listQuizOfClass = quizOfClassService.findQuizOfClassByIdClass(idClass);

        //add title
        listTitleQuizAndAssignment.add("StudentID");
        listTitleQuizAndAssignment.add("Student Name");
        for (AssignmentsOfClass assignmentOfClass : listAssignmentOfClass) {
            assignment = assignmentService.findAssignmentByidAssignment(assignmentOfClass.getIdAssignment());
            listTitleQuizAndAssignment.add(assignment.getTitle());

        }
        for (QuizOfClass quizOfClass : listQuizOfClass) {
            Quiz quiz = quizService.getQuizByIdQuiz(quizOfClass.getIdQuiz());
            listTitleQuizAndAssignment.add(quiz.getNameQuiz());
        }

        //tim tat ca user trong class
        listUserOfClass = userOfClassService.findUserOfClassByIdClass(idClass);
        for (UserOfClass userOfClass : listUserOfClass) {
            listUser.add(userService.getUserByIdUser(userOfClass.getIdUser()));
        }

        for (User user : listUser) {
            ArrayList<Double> listGrade = new ArrayList<Double>();
            //lay diem Assignment cua user
            for (AssignmentsOfClass assignmentOfClass : listAssignmentOfClass) {
                assignment = assignmentService.findAssignmentByidAssignment(assignmentOfClass.getIdAssignment());
                AssignmentsOfUser asu = assignmentOfUserService.findAssignmentOfUserByIdAssignmentAndIdUser(assignment.getIdAssignment(), user.getIdUser());
                if (asu != null) {
                    listGrade.add(asu.getGrade());
                } else {
                    listGrade.add(-1.0);
                }
            }

            //lay diem quiz cua User
            double max = 0;
            for (QuizOfClass quizOfClass : listQuizOfClass) {
                Quiz quiz = quizService.getQuizByIdQuiz(quizOfClass.getIdQuiz());
                List<QuizOfUser> qou = quizOfUserService.findQuizOfUserByIdUserAndIdQuiz(user.getIdUser(), quiz.getIdQuiz());
                if (qou.size() != 0) {
                    max = qou.get(0).getGrade();
                    for (int i = 1; i < qou.size(); i++) {
                        if (qou.get(i).getGrade() > max) {
                            max = qou.get(i).getGrade();
                        }
                    }
                    listGrade.add(max);
                } else {
                    listGrade.add(-1.0);
                }
            }

            infoStudent.put(user, listGrade);
        }
        model.addAttribute("infoStudent", infoStudent);
        model.addAttribute("nameClass", classService.getClassById(idClass).getNameClass());
        model.addAttribute("titles", listTitleQuizAndAssignment);
        session.setAttribute("idClass", idClass);
        return "teacher-class-report";
    }

    @GetMapping("/assignmentReport")
    public String teacherAsignmentReportPage(HttpSession session, Model model, @RequestParam(name = "idAssignment") int idAssignment,
            @RequestParam(name = "idClass") int idClass, @RequestParam("page") int pageNumber) {
        Map<Integer, User> mapUser = userService.findAll().stream().collect(Collectors.toMap(User::getIdUser, user -> user));

        //GET GRADE OF USER BY IDCLASS AND ID ASSIGNMENT
//        Map<Integer, Double> mapGrade = reportService.customFindByIdClassAndIdAssignment(idClass, idAssignment)
//                .stream().collect(Collectors.toMap(Report::getIdUser, report -> report.getGrade()));
        //GET LIST ASSIGNMENTOFUSER BY ID ASSIGNMENT AND ID CLASS
        Page<AssignmentsOfUser> listAssignemntOfUser = assignmentOfUserService.findByIdAssignmentAndIdClass(idAssignment, idClass, PageRequest.of(pageNumber - 1, 5));

        model.addAttribute("idClass", idClass);
        model.addAttribute("idAssignment", idAssignment);
        model.addAttribute("page", pageNumber);
        model.addAttribute("mapUser", mapUser);
//        model.addAttribute("mapGrade", mapGrade);
        model.addAttribute("assignmentsOfUsers", listAssignemntOfUser);

        return "teacher-assignment-report";
    }

    @GetMapping("/quiz/showListQuiz/{idClass}/{page}")
    public String showListQuizOfClass(HttpSession session, Model model, @PathVariable("idClass") int idClass,
            @PathVariable("page") int page, HttpServletRequest request) {
        String search = "";
        if (request.getParameter("txtSearch") != null) {
            search = request.getParameter("txtSearch");
        }
        Class c = classService.getClassById(idClass);
        String idSubject = c.getIdSubject();
        session.setAttribute("idSubject", idSubject);
        model.addAttribute("quizes", quizService.searchQuiz(search, idClass, PageRequest.of(page - 1, 4)));
        model.addAttribute("search", search);
        session.setAttribute("idClass", idClass);
        model.addAttribute("page", page);
        model.addAttribute("className", classService.getClassById(idClass).getNameClass());
        return "teacher-quiz";
    }

    @GetMapping("/quiz/create")
    public String viewCreatePage(HttpSession session) {
        session.removeAttribute("questions");
        return "teacher-quiz-add";
    }

    @PostMapping("/quiz/create/save")
    public String createQuiz(HttpSession session, HttpServletRequest request, Model model) throws Exception {
        String url = "";
        int idClass = -1;
        String idSubject = (String) session.getAttribute("idSubject");
        if (session.getAttribute("idClass") != null) {
            idClass = (int) session.getAttribute("idClass");
            url = "redirect:/teacher/quiz/showListQuiz/" + idClass + "/1";
        } else {
            //Cần para idSubject request hoặc session đều đc.
            url = "redirect:/teacher/quiz/showQuizesOfTeacher/" + idSubject + "/1";
        }

        String action = request.getParameter("action");
        String nameQuiz = request.getParameter("nameQuiz");
        int timeLimit = Integer.parseInt(request.getParameter("timeLimit"));
        Date openDate = df.parse(request.getParameter("openDate"));
        Date dueDate = df.parse(request.getParameter("dueDate"));
        String status = request.getParameter("status");

        List<Question> questions = null;
        CustomUserDetail userDetail = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (action.equals("addQuestion")) {
            Quiz quiz = new Quiz();
            quiz.setNameQuiz(nameQuiz);
            quiz.setTimeLimit(timeLimit);
            quiz.setOpenDate(openDate);
            quiz.setDueDate(dueDate);
            quiz.setStatus(status);
            quiz.setIdUser(userDetail.getUser().getIdUser());
            idSubject = (String) session.getAttribute("idSubject");
            quiz.setIdSubject(idSubject);
            if (session.getAttribute("questions") != null) {
                questions = (List<Question>) session.getAttribute("questions");
            } else {
                questions = new ArrayList<>();
            }

            session.setAttribute("questions", questions);
            url = viewCreateQuestionPage(model, quiz, session);
        } else {
            if (action.equals("createQuiz")) {
                Quiz newQuiz = (Quiz) session.getAttribute("newQuiz");
                newQuiz.setNameQuiz(nameQuiz);
                newQuiz.setTimeLimit(timeLimit);
                newQuiz.setOpenDate(openDate);
                newQuiz.setDueDate(dueDate);
                newQuiz.setStatus(status);
                newQuiz.setIdUser(userDetail.getUser().getIdUser());
                int numOfQues = Integer.parseInt(request.getParameter("numOfQues"));
                newQuiz.setNumOfQues(numOfQues);
                quizService.saveQuiz(newQuiz);
                questions = (List<Question>) session.getAttribute("questions");
                for (Question question : questions) {
                    questionService.save(question);
                }
                if (session.getAttribute("idClass") != null) {
                    QuizOfClass quizOfClass = new QuizOfClass();
                    quizOfClass.setIdQuiz(newQuiz.getIdQuiz());
                    quizOfClass.setIdClass(idClass);
                    quizOfClassService.save(quizOfClass);
                }
                session.removeAttribute("newQuiz");
            }
        }
        return url;
    }

    public String viewCreateQuestionPage(Model model, Quiz quiz, HttpSession session) {
        model.addAttribute("question", new Question());
        session.setAttribute("newQuiz", quiz);
        return "teacher-question-add";
    }

    @PostMapping("/quiz/addQuestion/create")
    public String createQuestion(@ModelAttribute("question") Question question, HttpSession session,
            HttpServletRequest request, Model model) {
        Quiz quiz = (Quiz) session.getAttribute("newQuiz");
        int correctAnswer = Integer.parseInt(request.getParameter("correctAnswer"));
        String[] answerContents = request.getParameterValues("answerContent");
        question.setCreateDate(new Date());
        question.setStatus(true);
        question.setQuiz(quiz);
        List<Answer> answers = question.getAnswers();
        for (int i = 0; i < answerContents.length; i++) {
            Answer answer = new Answer();
            answer.setContent(answerContents[i]);
            if (i == correctAnswer) {
                answer.setIsCorrect(true);
            } else {
                answer.setIsCorrect(false);
            }
            answer.setQuestion(question);
            answers.add(answer);
        }
        question.setAnswers(answers);

        List<Question> questions = (List<Question>) session.getAttribute("questions");
        questions.add(question);
        session.setAttribute("questions", questions);
        String url = "";
        if (request.getParameter("action") != null) {
            if (request.getParameter("action").equals("done")) {
                session.setAttribute("newQuiz", quiz);
                url = "teacher-quiz-add";
            }
        } else {
            url = viewCreateQuestionPage(model, quiz, session);
        }
        return url;
    }

    @GetMapping("/quiz/edit")
    public String editQuiz(Model model, HttpServletRequest request, HttpSession session) {
        int idQuiz = Integer.parseInt(request.getParameter("idQuiz"));
        Quiz quiz = quizService.getQuizByIdQuiz(idQuiz);
        if (quiz.getQuestions() != null) {
            quiz.getQuestions().removeIf(question -> question.isStatus() == false);
        }
        model.addAttribute("quiz", quiz);
        return "teacher-quiz-edit";
    }

    @PostMapping("/quiz/update")
    public String updateQuiz(@ModelAttribute("quiz") Quiz quiz, HttpSession session) {
        String url = "";
        if (session.getAttribute("idClass") != null) {
            int idClass = (int) session.getAttribute("idClass");
            url = "redirect:/teacher/quiz/showListQuiz/" + idClass + "/1";
        } else {
            if (session.getAttribute("idSubject") != null) {
                String idSubject = (String) session.getAttribute("idSubject");
                url = "redirect:/teacher/quiz/showQuizesOfTeacher/" + idSubject + "/1";
            }
        }
        quizService.saveQuiz(quiz);
        return url;
    }

    @GetMapping("/quiz/questions/{idQuiz}/{page}")
    public String viewListQuestionPage(HttpSession session, Model model,
            @PathVariable("idQuiz") int idQuiz, @PathVariable("page") int page) {
        Page<Question> questions = questionService.getQuestionsByIdQuiz(idQuiz, PageRequest.of(page - 1, 4));
        model.addAttribute("questions", questions);
        session.setAttribute("editingQuiz", quizService.getQuizByIdQuiz(idQuiz));
        model.addAttribute("page", page);
        model.addAttribute("newQuestion", new Question());
        return "teacher-question";
    }

    @GetMapping("/quiz/question/editQuestion/{idQuestion}")
    public String editQuestionPage(HttpSession session, Model model, HttpServletRequest request, @PathVariable("idQuestion") int idQuestion) {
        Question question = questionService.findbyId(idQuestion);
        model.addAttribute("question", question);
        session.setAttribute("answers", question.getAnswers());
        return "teacher-question-update";
    }

    @PostMapping("/quiz/question/editQuestion/update")
    public String updateQuestion(HttpSession session, @ModelAttribute("question") Question question, HttpServletRequest request) {
        Quiz quiz = question.getQuiz();
        List<Answer> ans = (List<Answer>) session.getAttribute("answers");
        int correctAnswer = Integer.parseInt(request.getParameter("correctAnswer"));
        String[] answerContents = request.getParameterValues("answerContent");
        int i = 0;
        for (Answer answer : ans) {
            answer.setContent(answerContents[i]);
            if (i == correctAnswer) {
                answer.setIsCorrect(true);
            } else {
                answer.setIsCorrect(false);
            }
            i++;
            question.getAnswers().add(answer);
        }
        questionService.save(question);
        if (!question.isStatus()) {
            int readyQuestion = questionService.countNumOfQuesOfQuiz(quiz.getIdQuiz());
            if (quiz.getNumOfQues() > readyQuestion) {
                quiz.setNumOfQues(readyQuestion);
                quizService.saveQuiz(quiz);
            }
        }
        return "redirect:/teacher/quiz/questions/" + quiz.getIdQuiz() + "/1";
    }

    @GetMapping("/quiz/question/deleteQuestion/{idQuestion}")
    public String deleteQuestion(@PathVariable("idQuestion") int idQuestion) {
        Question question = questionService.findbyId(idQuestion);
        question.setStatus(false);
        questionService.save(question);
        Quiz quiz = question.getQuiz();
        int readyQuestion = questionService.countNumOfQuesOfQuiz(quiz.getIdQuiz());
        if (quiz.getNumOfQues() > readyQuestion) {
            quiz.setNumOfQues(readyQuestion);
            quizService.saveQuiz(quiz);
        }
        return "redirect:/teacher/quiz/questions/" + quiz.getIdQuiz() + "/1";
    }

    @PostMapping("/quiz/question/create")
    public String createNewQuestion(@ModelAttribute("newQuestion") Question question, HttpServletRequest request) {
        int idQuiz = Integer.parseInt(request.getParameter("idQuiz"));
        int correctAnswer = Integer.parseInt(request.getParameter("correctAnswer"));
        String[] answerContents = request.getParameterValues("answerContent");
        question.setCreateDate(new Date());
        question.setStatus(true);
        question.setQuiz(quizService.getQuizByIdQuiz(idQuiz));
        List<Answer> answers = question.getAnswers();
        for (int i = 0; i < answerContents.length; i++) {
            Answer answer = new Answer();
            answer.setContent(answerContents[i]);
            if (i == correctAnswer) {
                answer.setIsCorrect(true);
            } else {
                answer.setIsCorrect(false);
            }
            answer.setQuestion(question);
            answers.add(answer);
        }
        question.setAnswers(answers);
        questionService.save(question);
        return "redirect:/teacher/quiz/questions/" + idQuiz + "/1";
    }

    @GetMapping("/home")
    public String showTeacherHome(Model model
    ) {
        return userController.listByPage(model, 1);
    }

    @GetMapping("/classPage/{pageNumber}/{id}")
    public String listByPageClassTeacher(Model model,
            @PathVariable(name = "pageNumber") Integer currentPage,
            @PathVariable(name = "id") String idSubject
    ) {
        User u = new User();
        Page<Class> page = classService.getListClassRoleTeacher(currentPage, idSubject);
        int totalPages = page.getTotalPages();
        List<Class> list = page.getContent();
        if (list != null) {
            model.addAttribute("idSubject", idSubject);
            if (list.size() != 0) {
                model.addAttribute("currentPage", currentPage);
                model.addAttribute("totalPages", totalPages);
                model.addAttribute("nameUser", u.getFullName());
                model.addAttribute("listClass", list);
            } else {
                model.addAttribute("message", "Empty Class!");
            }
        }
        return "teacherClass";
    }

    @GetMapping("/subject/deleteClass/{id}")
    public String deleteClass(@PathVariable(name = "id") Integer id
    ) {
        String idSubject = classService.delete(id);
        return "redirect:/teacher/subject/" + idSubject;
    }

    @GetMapping("/subject/restoreClass/{id}")
    public String restoreClass(@PathVariable(name = "id") Integer id
    ) {
        String idSubject = classService.restore(id);
        return "redirect:/teacher/subject/" + idSubject;
    }

    @GetMapping("/subject/{id}")
    public String enrollSubject(@PathVariable(name = "id") String idSubject, Model model
    ) {
        return listByPageClassTeacher(model, 1, idSubject);
    }

    @GetMapping("/subject/editClass/{id}")
    public ModelAndView editClassFormPage(@PathVariable(name = "id") Integer id
    ) {
        ModelAndView mav = new ModelAndView("teacherEditClass");
        Class c = classService.getClassById(id);
        mav.addObject("class", c);
        return mav;
    }

    @PostMapping("/subject/editClass")
    public String editClassPage(@ModelAttribute("class") Class c, Model model
    ) {
        boolean checkEmpty = true;
        String error = "";
        if ("".trim().equals(c.getNameClass())) {
            error += "Class name cannot be blank! ";
            checkEmpty = false;
        }
        if ("".trim().equals(c.getPassword())) {
            error += "Password cannot be blank!";
            checkEmpty = false;
        }
        if (checkEmpty) {
            classService.save(c);
            model.addAttribute("message", "Update successful!");
        } else {
            model.addAttribute("msgError", error);
        }
        return "teacherEditClass";
    }

    @GetMapping("/subject/createClass/{id}")
    public String createClassFormPage(Model model,
            @PathVariable(name = "id") String idSubject
    ) {
        Class c = new Class();
        c.setIdSubject(idSubject);
        model.addAttribute("class", c);
        return "teacherCreateClass";
    }

    @PostMapping("/subject/createClass")
    public String createClassPage(@ModelAttribute("class") Class c, Model model
    ) {
        boolean checkEmpty = true;
        String error = "";
        if ("".trim().equals(c.getNameClass())) {
            error += "Class name cannot be blank! ";
            checkEmpty = false;
        }
        if ("".trim().equals(c.getPassword())) {
            error += "Password cannot be blank!";
            checkEmpty = false;
        }
        if (checkEmpty) {
            c.setStatus(true);
            classService.save(c);
            return "redirect:/teacher/subject/" + c.getIdSubject();
        } else {
            model.addAttribute("msgError", error);
            return "teacherCreateClass";
        }
    }

    @RequestMapping("/account")
    public String teacherAccountPage(Model model
    ) {
        CustomUserDetail user = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user.getUser());

        return "teacher-account";
    }

    @RequestMapping(value = "/update-account-page")
    public String updateTeacherAccountPage(@RequestParam(name = "idUser") int idUser, Model model
    ) {
        //update account page
        User user = service.loadUserByIdUser(idUser);

        if (user != null) {
            model.addAttribute("user", user);
        }
        return "teacher-update-account";
    }

    @PostMapping(value = "/update-account")
    public String updateTeacherAccount(@ModelAttribute(name = "user") User user,
            @RequestParam(name = "confirm_password") String confirmPassword,
            Model model
    ) {
        //Validate data from attribute
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        //Error map
        HashMap<String, String> error = new HashMap<>();
        String message = null;

        //check unique email
        boolean isEmailUnique = service.isEmailUniqueUpdate(user.getEmail(), user.getIdUser());
        if (!isEmailUnique) {
            error.put("emailError", "This email address was already being used");
        }

        //check match password confirm
        boolean isPasswordMatch = user.getPassword().equalsIgnoreCase(confirmPassword);
        if (!isPasswordMatch) {
            error.put("confirmPasswordError", "Confirm password not match");
        }

        if (violations.isEmpty() && error.isEmpty() && isPasswordMatch) {
            try {
                service.updateAdminAcount(user);
                model.addAttribute("message", "Successfully update account : " + user.getEmail());
                return "forward:/teacher/account";
            } catch (Exception e) {
                e.printStackTrace();
                message = "Error while update this account";
            }
        } else {
            for (ConstraintViolation<User> violation : violations) {
                error.put(violation.getPropertyPath() + "Error", violation.getMessageTemplate());
            }
        }

        model.addAttribute("error", error);
        model.addAttribute("message", message);
        model.addAttribute("user", user);

        return "forward:/teacher/update-account-page";
    }
}

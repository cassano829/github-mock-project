/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.controller;

import com.mockproject.model.Answer;
import com.mockproject.service.QuizOfStudentService;
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
import com.mockproject.repository.QuizOfClassRepository;
import com.mockproject.service.QuestionService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
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
    QuizOfStudentService quizOfStudentService;
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
    QuizOfClassRepository quizOfClassRepository;

    @Autowired
    UserDetailServiceImp userService;

    @Autowired
    UserDetailServiceImp service;

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

//    @GetMapping("/quiz/viewListQuizStudent")
//    public String viewListQuizOfStudent(HttpServletRequest request,Model model){
//        int idQuiz=Integer.parseInt(request.getParameter("idQuiz"));
//        model.addAttribute("quizOfStudents",quizOfStudentService.getListQuizOfStudentByIdQuiz(idQuiz));
//        return "quizOfClass";
//    }
    @GetMapping("/quiz/create")
    public String viewCreatePage() {
        return "teacher-quiz-add";
    }

    @PostMapping("/quiz/create/save")
    public String createQuiz(HttpSession session, HttpServletRequest request, Model model) throws Exception {
        int idClass = (int) session.getAttribute("idClass");
        String url = "redirect:/teacher/quiz/showListQuiz/" + idClass + "/1";
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
            String idSubject = (String) session.getAttribute("idSubject");
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
                QuizOfClass quizOfClass = new QuizOfClass();
                quizOfClass.setIdQuiz(newQuiz.getIdQuiz());
                quizOfClass.setIdClass(idClass);
                quizOfClassRepository.save(quizOfClass);
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
        Set<Answer> answers = question.getAnswers();
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
        int idClass = (int) session.getAttribute("idClass");
        quizService.saveQuiz(quiz);
        return "redirect:/teacher/quiz/showListQuiz/" + idClass + "/1";
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
        Set<Answer> ans = (Set<Answer>) session.getAttribute("answers");
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
        Set<Answer> answers = question.getAnswers();
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

    @GetMapping("/account")
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

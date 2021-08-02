/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.controller;

import com.mockproject.model.Answer;
import com.mockproject.security.CustomUserDetail;
import com.mockproject.service.ClassService;
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

/**
 *
 * @author Asus
 */
@Controller
@RequestMapping("/teacher")
public class TeacherController {

    public final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

    @Autowired
    QuizOfStudentService quizOfStudentService;

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

    @GetMapping("/subject/showAllSubjectOfTeacher/{page}")
    public String showListSubject(Model model, @PathVariable("page") int page) {
        model.addAttribute("listSubject", subjectService.getAllSubject(PageRequest.of(page - 1, 4)));
        model.addAttribute("page", page);
        return "teacher-subject";
    }

    @GetMapping("/class/showClassOfTeacher/{idSubject}/{page}")
    public String showClassOfTeacher(Model model, @PathVariable("idSubject") String idSubject, HttpSession session, @PathVariable("page") int page) {
        CustomUserDetail userDetail = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Page<Class> classes = classService.getListClassByIdTeacherAndIdSubject(userDetail.getUser().getIdUser(), idSubject, PageRequest.of(page - 1, 4));
        model.addAttribute("classesOfTeacher", classes);
        model.addAttribute("page", page);
        session.setAttribute("idSubject", idSubject);
        return "teacher-class";
    }

    @GetMapping("/quiz/showListQuiz/{idClass}/{page}")
    public String showListQuizOfClass(HttpSession session, Model model, @PathVariable("idClass") int idClass,
            @PathVariable("page") int page, HttpServletRequest request) {
        String search = "";
        if (request.getParameter("txtSearch") != null) {
            search = request.getParameter("txtSearch");
        }
        model.addAttribute("quizes", quizService.searchQuiz(search, idClass, PageRequest.of(page - 1, 4)));
        model.addAttribute("search", search);
        session.setAttribute("idClass", idClass);
        model.addAttribute("page", page);
        model.addAttribute("className", classService.findClassById(idClass).getNameClass());
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
            quiz.getQuestions().removeIf(question -> question.isStatus()==false);
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
        model.addAttribute("editingQuiz", quizService.getQuizByIdQuiz(idQuiz));
        model.addAttribute("page", page);
        model.addAttribute("newQuestion", new Question());
        return "teacher-question";
    }

    @GetMapping("/quiz/question/editQuestion/{idQuestion}")
    public String editQuestionPage(Model model, HttpServletRequest request, @PathVariable("idQuestion") int idQuestion) {
        model.addAttribute("question", questionService.findbyId(idQuestion));
        return "teacher-question-update";
    }

    @PostMapping("/quiz/question/editQuestion/update")
    public String updateQuestion(@ModelAttribute("question") Question question, HttpServletRequest request) {
        Quiz quiz = question.getQuiz();
        int correctAnswer = Integer.parseInt(request.getParameter("correctAnswer"));
        String[] answerContents = request.getParameterValues("answerContent");
        int i = 0;
        for (Answer answer : question.getAnswers()) {
            answer.setContent(answerContents[i]);
            if (i == correctAnswer) {
                answer.setIsCorrect(true);
            } else {
                answer.setIsCorrect(false);
            }
            i++;
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
}

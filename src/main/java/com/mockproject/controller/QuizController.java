/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.controller;

import com.mockproject.model.Question;
import com.mockproject.model.QuizCart;
import com.mockproject.service.QuestionService;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Asus
 */
@Controller
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    QuestionService questionService;
//    @Autowired
//    AnswerService answerService;

    @GetMapping("/showQuiz")
    public String showQuiz(HttpSession session,
            Model model, Principal principal) {
        //Them para txtSubject
        String idSubject = "S001";
        List<Question> questions = questionService.findListQuestionByidSubject(idSubject);
        session.setAttribute("questions", questions);
        QuizCart cart = new QuizCart(principal.getName());
        session.setAttribute("quizCart", cart);
        session.setAttribute("questionIndex", 0);
        model.addAttribute("txtSubjectId", idSubject);
        Timestamp startQuiz = new Timestamp(System.currentTimeMillis());
        session.setAttribute("TimeStartQuiz", startQuiz);
        model.addAttribute("timeLimit", 3);
        return "quiz";
    }

    @GetMapping("/chooseQuestion")
    public String add(HttpSession session, HttpServletRequest request, Model model) {
        int questionIndexAfter = 0;
        int idQuestion = Integer.parseInt(request.getParameter("questionId"));
        int userAnswer = -1;
        try {
            questionIndexAfter = Integer.parseInt(request.getParameter("questionIndex"));
        } catch (Exception e) {
            //Ghi loi ra file log
        }
        try {
            userAnswer = Integer.parseInt(request.getParameter("answer"));
        } catch (Exception e) {
            //Ghi loi ra file log
        }
        int questionIndexNow = (Integer) session.getAttribute("questionIndex");
        QuizCart cart = (QuizCart) session.getAttribute("quizCart");
        cart.getQuizCart().put(questionIndexNow, userAnswer);
        session.setAttribute("quizCart", cart);
        session.setAttribute("questionIndex", questionIndexAfter);
        String subjectId = request.getParameter("txtSubjectId");
        model.addAttribute("txtSubjectId", subjectId);
        return "quiz";
    }
    
    @GetMapping("/submit")
    public String submitQuiz(HttpSession session,Model model,HttpServletRequest request){
        QuizCart cart=(QuizCart) session.getAttribute("quizCart");
        
        
        return "report";
    }
}

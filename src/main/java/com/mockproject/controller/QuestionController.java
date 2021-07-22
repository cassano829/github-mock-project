/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.controller;

import com.mockproject.model.Answer;
import com.mockproject.model.Question;
import com.mockproject.service.AnswerService;
import com.mockproject.service.QuestionService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Asus
 */
@Controller
public class QuestionController {

//    @Autowired
//    AnswerService answerService;

//    @Autowired
//    QuestionService questionService;
//    
//    @GetMapping("/addQuestion")
//    public String showAddNewQuestionForm(Model model){
//        model.addAttribute("question",new Question());
//        return "";
//    }
//    
//    @PostMapping("/question/save")
//    public String createQuestion(@ModelAttribute("question") Question question,
//            @RequestParam("answer1") String answerContent1,@RequestParam("answer2") String answerContent2,
//            @RequestParam("answer3") String answerContent3,@RequestParam("answer4") String answerContent4,
//            @RequestParam("answerCorrect") String answerCorrect,BindingResult bindingResult, Model model) throws IOException{
//        //valid question
//        boolean isValid=true;
//        if(isValid){
//            questionService.save(question);
//            Answer answer1=new Answer();
//
//            
//            Answer answer2=new Answer();
//
//            
//            Answer answer3=new Answer();
//
//            
//            Answer answer4=new Answer();
//
//            
//            model.addAttribute("message", "Add succesfull");
//        }else{
//            //....
//        }
//        return "";
//    }
//    
    
}

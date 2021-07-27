/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.service;

import com.mockproject.model.QuizOfStudent;
import com.mockproject.model.User;
import com.mockproject.repository.QuizOfStudentRepository;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Asus
 */
@Service
public class QuizOfStudentService {
    
    public final SimpleDateFormat df = new SimpleDateFormat("yyyy-MMM-dd hh:mm:ss");
    
    @Autowired
    QuizOfStudentRepository repository;
    
    @Autowired
    MailService mailService;

    
    public void save(QuizOfStudent quizOfStudent){
        repository.save(quizOfStudent);
    }
    public int getIdOfQuizOfUser(){
        return repository.getIdOfQuizOfUser();
    }
    
    public void sendEmailToNotifyQuiz(User user,int totalCorrect,int numOfQues){
        String subject = "Notification for your quiz";
        String senderName = "Learning Management System Team";
        float score=10/numOfQues*totalCorrect;
        String mailContent = "<p>Dear " + user.getFullName() + ",<br>"
                + "The following quiz submission was recorded by LMS.admin: <br>"
                + "Student                 : "+user.getFullName()+" ("+user.getEmail()+")<br>"
                + "Submitted Date          : "+df.format(new Date())+"<br>"
                + "Number of correct answer: "+totalCorrect+"/"+numOfQues+"<br>"
                + "Score                   : "+score+"</p>";
        mailContent += "<p>Thank you,<br> Learning Management System Team</p>";
        mailService.sendMailToNotify(subject, senderName, mailContent, user.getEmail());
    }
}

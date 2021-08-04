package com.mockproject.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mockproject.repository.QuizOfUserRepository;
import com.mockproject.model.QuizOfUser;
import com.mockproject.model.User;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class QuizOfUserService {

    public final SimpleDateFormat df = new SimpleDateFormat("yyyy-MMM-dd hh:mm:ss");

    @Autowired
    MailService mailService;

    @Autowired
    private QuizOfUserRepository quizOfUserRepository;

    public void save(QuizOfUser quizOfStudent) {
        quizOfUserRepository.save(quizOfStudent);
    }

    public int getIdOfQuizOfUser() {
        return quizOfUserRepository.getIdOfQuizOfUser();
    }

    public void sendEmailToNotifyQuiz(User user, int totalCorrect, int numOfQues) {
        String subject = "Notification for your quiz";
        String senderName = "Learning Management System Team";
        double score = 10.0 / numOfQues * totalCorrect;
        String mailContent = "<p>Dear " + user.getFullName() + ",<br>"
                + "The following quiz submission was recorded by LMS.admin: <br>"
                + "Student                 : " + user.getFullName() + " (" + user.getEmail() + ")<br>"
                + "Submitted Date          : " + df.format(new Date()) + "<br>"
                + "Number of correct answer: " + totalCorrect + "/" + numOfQues + "<br>"
                + "Score                   : " + score + "</p>";
        mailContent += "<p>Thank you,<br> Learning Management System Team</p>";
        mailService.sendMailToNotify(subject, senderName, mailContent, user.getEmail());
    }

    public List<QuizOfUser> findQuizOfUserByIdUserAndIdQuiz(int idUser, int idQuiz) {
        return quizOfUserRepository.findQuizOfUserByIdUserAndIdQuiz(idUser, idQuiz);
    }

    public Double findMaxQuizGrade(int idUser, int idQuiz) {
        return quizOfUserRepository.findMaxQuizGrade(idUser, idQuiz);
    }

    public QuizOfUser findByIdQuizOfUser(int idQuizOfUser) {
        return quizOfUserRepository.findByIdQuizOfUser(idQuizOfUser);
    }

}

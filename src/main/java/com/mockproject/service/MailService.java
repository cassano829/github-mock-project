/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.service;

import com.mockproject.model.User;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 *
 * @author ACER
 */
@Service
public class MailService {

    @Autowired
    JavaMailSender javaMailSender;

    public void sendMail(User user, String code) {
        String subject = "Verify your registration";
        String senderName = "Learning Management System Team";
        String mailContent = "<h3>Dear " + user.getFullName() + ", please verify your account"
                + " with the code: " + code + "</h3>";
        mailContent += "<p>Thank you,<br> Learning Management System Team</p>";

        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg);
        try {
            helper.setFrom("lmsteamproject@gmail.com", senderName);
            helper.setTo(user.getEmail());
            helper.setSubject(subject);
            helper.setText(mailContent, true);
            javaMailSender.send(msg);
        } catch (MessagingException | UnsupportedEncodingException ex) {
            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

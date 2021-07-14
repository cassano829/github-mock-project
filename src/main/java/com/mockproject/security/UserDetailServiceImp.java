/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.security;

import com.mockproject.controller.MailController;
import com.mockproject.model.User;
import com.mockproject.repository.UserRepository;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author ACER
 */
@Service
public class UserDetailServiceImp implements UserDetailsService {

    public final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    UserRepository repo;

    @Autowired
    MailController mailSender;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repo.findUserByEmail(email);
        if (user == null || !user.isStatus()) {
            throw new UsernameNotFoundException(email);
        }
        return new CustomUserDetail(user);
    }

    public void register(User user) {
        encodePassword(user);
        user.setCreateDate(df.format(new Date()));
        user.setStatus(false);

        Random rnd = new Random();
        int num = rnd.nextInt(999999);
        String rdn = String.format("%06d", num);
        user.setVerificationCode(rdn);

//        String random = RandomString.make(64);

        repo.save(user);
        mailSender.sendMail(user, rdn);
    }

    public void encodePassword(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    public User getUserByUsername(String email) {
        User user = repo.findUserByEmail(email);
        return user;
    }

    public boolean isEmailUnique(String email) {
        User existedUser = repo.findUserByEmail(email); 
        return existedUser == null; 
    }

    public boolean isValidEmail(String email) {
        String regex = "[a-z][a-z0-9_.]{4,32}@[a-z0-9]{2,}([.][a-z0-9]{2,4}){1,2}";
        return email.matches(regex);
    }

    public boolean verify(String verificationCode) {
        User user = repo.findByVerificationCode(verificationCode);
        if (user == null || user.isStatus()) {
            return false;
        } else {
            user.setStatus(true);
            repo.save(user);
            return true;
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.security;

import com.mockproject.model.Role;
import com.mockproject.model.User;
import com.mockproject.repository.UserRepository;
import com.mockproject.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 *
 * @author ACER
 */
@Service
public class UserDetailServiceImp implements UserDetailsService {

    public final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    UserRepository repo;

    @Autowired
    MailService mailSender;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repo.findUserByEmail(email);
        if (user == null || !user.isStatus()) {
            throw new UsernameNotFoundException(email);
        }
        return new CustomUserDetail(user);
    }

    public List<User> loadAllUsers(){
        return repo.findAll();
    }

    public User loadUserByIdUser(int idUser) {
        User user = repo.findUsersByIdUser(idUser);
        if (user == null || !user.isStatus()) {
            throw new UsernameNotFoundException(Integer.toString(idUser));
        }
        return user;
    }

    public User loadUserByIdUserNonFilter(int idUser) {
        User user = repo.findUsersByIdUser(idUser);
        if (user == null) {
            throw new UsernameNotFoundException(Integer.toString(idUser));
        }
        return user;
    }

    public void updateAdminAcount(User user){
        encodePassword(user);

        User tmp = loadUserByIdUser(user.getIdUser());
        if(tmp != null){
            tmp.setFullName(user.getFullName());
            tmp.setEmail(user.getEmail());
            tmp.setPassword(user.getPassword());
        }
        repo.save(tmp);
    }

    public void updateUserAccount(User user){
        User tmp = loadUserByIdUserNonFilter(user.getIdUser());
        if(tmp != null) {
            tmp.setFullName(user.getFullName());
            tmp.setEmail(user.getEmail());
            tmp.setStatus(user.isStatus());
        }
        repo.save(tmp);
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

    public int registerTeacher(User teacher){
        encodePassword(teacher);
        teacher.setCreateDate(df.format(new Date()));
        teacher.setStatus(true);
        teacher = repo.save(teacher);
        repo.flush();

        return teacher.getIdUser();
    }

    public void encodePassword(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    public User getUserByEmail(String email) {
        User user = repo.findUserByEmail(email);
        return user;
    }
    
    public User getUserByIdUser(int id) {
        User user = repo.findUserByIdUser(id);
        return user;
    }

    public boolean isEmailUnique(String email) {
        User existedUser = repo.findUserByEmail(email);
        return existedUser == null;
    }

    public boolean isEmailUniqueUpdate(String email,int idUser) {
        User existedUser = repo.findUserByEmail(email);
        if(existedUser == null){
            return true;
        }
        return idUser == existedUser.getIdUser();

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

    public List<User> searchUser(String email,Role role,boolean status){ return repo.findAllByEmailContainsAndRolesAndStatus(email,role,status);}
}

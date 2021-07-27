/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.service;

import com.mockproject.repository.ClassRepository;
import java.text.SimpleDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.mockproject.model.Class;
import com.mockproject.security.CustomUserDetail;
import java.util.Date;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author ACER
 */
@Service
public class ClassService {

    public final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    ClassRepository repo;

    public Page<Class> getListClass(int pageNumber, String idSubject) {
        CustomUserDetail user = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Pageable pageable = PageRequest.of(pageNumber - 1, 4);
        return repo.findAllWithStatus(pageable, user.getUser().getIdUser(), idSubject);
    }

    public void save(Class c) {
        CustomUserDetail user = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        c.setIdUser(user.getUser().getIdUser());
        c.setCreateDate(df.format(new Date()));
        repo.save(c);
    }

    public Class getClassById(int id) {
        return repo.getById(id);
    }

    public String delete(Integer id) {
        Class c = getClassById(id);
        c.setStatus(false);
        save(c);
        return c.getIdSubject();
    }

    public String restore(Integer id) {
        Class c = getClassById(id);
        c.setStatus(true);
        save(c);
        return c.getIdSubject();
    }
}

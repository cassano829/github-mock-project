/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.service;

import com.mockproject.model.Subject;
import com.mockproject.repository.SubjectRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Asus
 */
@Service
public class SubjectService {
    
    @Autowired
    SubjectRepository repository;
    
    public List<Subject> getAllSubject(){
        return repository.findAll();
    }
}

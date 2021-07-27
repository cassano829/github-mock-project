/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.service;

import com.mockproject.repository.ClassRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mockproject.model.Class;

/**
 *
 * @author Asus
 */
@Service
public class ClassSerivice {

    @Autowired
    ClassRepository classRepository;
    
    public List<Class> getListClassByIdTeacher(int idTeacher){
        return classRepository.findByIdUser(idTeacher);
    }

    public List<Class> getListClassByIdTeacherAndIdSubject(int idTeacher,String idSubject) {
        List<Class> classes = classRepository.findByIdUserAndIdQuiz(idTeacher,idSubject);
        return classes;
    }
    
    public Class findClassById(int idClass){
        return classRepository.getById(idClass);
    }
}

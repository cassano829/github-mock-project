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
import com.mockproject.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Asus
 */
@Service
public class ClassService {

    @Autowired
    ClassRepository classRepository;
    
//    public List<Class> getListClassByIdTeacher(int idTeacher){
//        return classRepository.findByIdUser(idTeacher);
//    }

    public Page<Class> getListClassByIdTeacherAndIdSubject(int idTeacher,String idSubject,Pageable pageable) {
        Page<Class> classes = classRepository.findByIdTeacherAndIdSubject(idTeacher,idSubject,pageable);
        return classes;
    }
    
    public Class findClassById(int idClass){
        return classRepository.getById(idClass);
    }
    
    public Page<Class> getAllClassByIdStudent(int idUser,Pageable pageable){
        return classRepository.findAllByIdUser(idUser,pageable);
    }
}

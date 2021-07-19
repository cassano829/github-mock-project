/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.repository;

import com.mockproject.model.Subject;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author truon
 */
public interface SubjectAssignmentRepository extends CrudRepository<Subject, String>{
    
    @Override
    public List<Subject> findAll();
}

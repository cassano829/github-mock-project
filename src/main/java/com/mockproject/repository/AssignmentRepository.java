/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.repository;

import com.mockproject.model.Assignment;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author truon
 */
public interface AssignmentRepository extends CrudRepository<Assignment, Integer>{
    
    @Override
    List<Assignment> findAll ();
    
    Assignment findByIdAssignment(Integer id);
    
}

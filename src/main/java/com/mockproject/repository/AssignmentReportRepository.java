/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.repository;

import com.mockproject.model.Report;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author truon
 */
public interface AssignmentReportRepository extends CrudRepository<Report, Integer>{
    
    public Report findByIdAssignmentAndIdClass(Integer idAssignment, Integer IdClass);
    
    public Report findByIdAssignmentAndIdClassAndIdUser(Integer idAssignment, Integer IdClass, Integer idUser);
}

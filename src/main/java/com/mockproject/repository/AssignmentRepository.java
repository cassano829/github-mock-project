/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.repository;

import com.mockproject.model.Assignment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author truon
 */
public interface AssignmentRepository extends CrudRepository<Assignment, Integer>, PagingAndSortingRepository<Assignment, Integer> {

    public Page<Assignment> findAllByIdSubjectAndIdUser(String id, Integer idUser, Pageable pageable);
    
    public Page<Assignment> findByIdSubject(String idSubject, Pageable pageAble);
}

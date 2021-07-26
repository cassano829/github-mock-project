/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author truon
 */
public interface AssignmentClassRepository extends CrudRepository<com.mockproject.model.Class, Integer>, PagingAndSortingRepository<com.mockproject.model.Class, Integer>{
    
    public Page<com.mockproject.model.Class> findByIdSubjectAndStatus(String idSubject, boolean status, Pageable pageAble);
    
    @Query(value = "from Class where idClass in (select aoc.idClass from AssignmentsOfClass aoc where aoc.idAssignment = ?1)")
    public List<com.mockproject.model.Class> getListClassByIdAssignment(Integer idAssignment);
    
}

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
import com.mockproject.model.Class;

/**
 *
 * @author truon
 */
public interface AssignmentClassRepository extends CrudRepository<com.mockproject.model.Class, Integer>, PagingAndSortingRepository<com.mockproject.model.Class, Integer>{
    
    public Page<Class> findByIdSubjectAndStatus(String idSubject, boolean status, Pageable pageAble);
    
    public Page<Class> findByIdSubjectAndIdUserAndStatus(String idSubject, Integer idUser, boolean status, Pageable pageable);
    
    @Query(value = "from Class where idClass in (select aoc.idClass from AssignmentsOfClass aoc where aoc.idAssignment = ?1)")
    public List<Class> getListClassByIdAssignment(Integer idAssignment);
    
}

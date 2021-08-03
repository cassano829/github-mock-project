/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.repository;

import com.mockproject.model.AssignmentsOfClass;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author truon
 */
public interface AssignmentOfClassRepository extends CrudRepository<AssignmentsOfClass, Integer>{
    
    public List<AssignmentsOfClass> findByIdAssignment(Integer idAssigment);
    
    public AssignmentsOfClass findByIdAssignmentAndIdClass(Integer idAssignment, Integer idClass);
    
    @Query("FROM AssignmentsOfClass aoc WHERE aoc.idAssignment = ?1 and aoc.idClass in (SELECT c.idClass FROM Class c WHERE c.idUser = ?2)")
    public List<AssignmentsOfClass> customFindByIdAssignmentAndIdUser(Integer idAssignment, Integer idUser);
    
}

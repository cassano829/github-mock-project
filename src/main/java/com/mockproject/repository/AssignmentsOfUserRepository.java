/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.repository;

import com.mockproject.model.AssignmentsOfUser;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author truon
 */
public interface AssignmentsOfUserRepository extends CrudRepository<AssignmentsOfUser, Integer>, PagingAndSortingRepository<AssignmentsOfUser, Integer> {

    public Page<AssignmentsOfUser> findByIdAssignmentAndIdClass(Integer idAssignment, Integer idClass, Pageable pageable);

    public AssignmentsOfUser findByIdAssignmentAndIdClassAndIdUser(Integer idAssignment, Integer idClass, Integer idUser);

    public List<AssignmentsOfUser> findByIdClassAndIdUser(Integer idClass, Integer idUser);

    AssignmentsOfUser findAssignmentOfUserByIdAssignmentAndIdUser(Integer idAssignment, Integer idUser);
}

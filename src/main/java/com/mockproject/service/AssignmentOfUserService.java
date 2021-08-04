/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.service;

import com.mockproject.model.AssignmentsOfUser;
import com.mockproject.repository.AssignmentsOfUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author ACER
 */
@Service
public class AssignmentOfUserService {

    @Autowired
    AssignmentsOfUserRepository repo;

    public Page<AssignmentsOfUser> findByIdAssignmentAndIdClass(int idAssignment, int idClass, Pageable pageable) {
        return repo.findByIdAssignmentAndIdClass(idAssignment, idClass, pageable);
    }

    public AssignmentsOfUser findAssignmentOfUserByIdAssignmentAndIdUser(int idAssignment, int idUser) {
        return repo.findAssignmentOfUserByIdAssignmentAndIdUser(idAssignment, idUser);
    }

}

package com.mockproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mockproject.model.Assignment;
import com.mockproject.repository.AssignmentRepository;

@Service
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    public Assignment findAssignmentByidAssignment(int id) {
        return assignmentRepository.findAssignmentByIdAssignment(id);
    }

}

package com.mockproject.service;

import com.mockproject.model.AssignmentsOfClass;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mockproject.repository.AssignmentOfClassRepository;

@Service
public class AssignmentOfClassService {

    @Autowired
    private AssignmentOfClassRepository assignmentOfClassRepository;

    public List<AssignmentsOfClass> findAssignmentOfClassByIdClass(int id) {
        return assignmentOfClassRepository.findAssignmentOfClassByIdClass(id);
    }
}

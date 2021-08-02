package com.mockproject.service;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mockproject.model.Subject;
import com.mockproject.repository.AssignmentOfClassRepository;
import com.mockproject.repository.ClassRepository;
import com.mockproject.repository.SubjectRepository;
import com.mockproject.model.AssignmentOfClass;
import com.mockproject.model.Assignment;
import com.mockproject.model.Class;
import com.mockproject.repository.AssignmentRepository;


@Service
public class AssignmentService {
	@Autowired
	private AssignmentRepository assignmentRepository;
	
	public Assignment findAssignmentByidAssignment(int id){
		return assignmentRepository.findAssignmentByIdAssignment(id);
	}
}



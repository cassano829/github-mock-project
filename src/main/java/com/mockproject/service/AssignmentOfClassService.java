package com.mockproject.service;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mockproject.model.Subject;
import com.mockproject.repository.AssignmentOfClassRepository;
import com.mockproject.model.AssignmentOfClass;
import com.mockproject.model.Class;


@Service
public class AssignmentOfClassService {
	@Autowired
	private AssignmentOfClassRepository assignmentOfClassRepository;
	
	public List<AssignmentOfClass> findAssignmentOfClassByIdClass(int id){
		return assignmentOfClassRepository.findAssignmentOfClassByIdClass(id);
	}
}



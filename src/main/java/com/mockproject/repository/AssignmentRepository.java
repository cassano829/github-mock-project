package com.mockproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mockproject.model.AssignmentOfClass;
import com.mockproject.model.Assignment;
import com.mockproject.model.Class;
import com.mockproject.model.Subject;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {
	Assignment findAssignmentByIdAssignment(int idClass);

}

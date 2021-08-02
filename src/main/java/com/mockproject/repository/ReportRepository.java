package com.mockproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mockproject.model.Class;
import com.mockproject.model.Question;
import com.mockproject.model.Report;
import com.mockproject.model.Subject;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {
	List<Report> findReportByAssignment_idAssignmentAndClas_idClass(int idAssignment,int idClass);
	
	List<Report> findDistinctByQuiz_idQuizAndClas_idClass(int idQuiz,int idClass);
	
	List<Report> findReportByQuiz_idQuizAndClas_idClassAndUser_idUser(int idQuiz,int idClass,int idUser);
	

	Report findReportByAssignment_idAssignmentAndClas_idClassAndUser_idUser(int idAssignment,int idClass,int idUser);
	
	
	@Query(value = "SELECT max(grade) FROM Report r WHERE r.user.idUser=?1 and r.clas.idClass=?2 and r.quiz.idQuiz=?3")
	public String findMaxQuizGrade(int idUser,int idClass,int idQuiz);
	
}

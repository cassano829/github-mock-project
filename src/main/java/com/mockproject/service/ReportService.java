package com.mockproject.service;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mockproject.model.Subject;
import com.mockproject.repository.ClassRepository;
import com.mockproject.repository.QuestionRepository;
import com.mockproject.repository.ReportRepository;
import com.mockproject.repository.SubjectRepository;
import com.mockproject.model.Class;
import com.mockproject.model.Question;
import com.mockproject.model.Report;


@Service
public class ReportService {
	@Autowired
	private ReportRepository reportRepository;
	
	public List<Report> findReportByAssignment_idAssignmentAndClas_idClass(int idAssignment,int idClass){
		return reportRepository.findReportByAssignment_idAssignmentAndClas_idClass(idAssignment,idClass);
	}
	
	public List<Report> findDistinctByQuiz_idQuizAndClas_idClass(int idQuiz,int idClass){
		return reportRepository.findDistinctByQuiz_idQuizAndClas_idClass(idQuiz,idClass);
	}
	
	public List<Report> findReportByQuiz_idQuizAndClas_idClassAndUser_idUser(int idQuiz,int idClass,int idUser){
		return reportRepository.findReportByQuiz_idQuizAndClas_idClassAndUser_idUser(idQuiz,idClass,idUser);
	}
	
	public Report findReportByAssignment_idAssignmentAndClas_idClassAndUser_idUser(int idAssignment,int idClass,int idUser){
		return reportRepository.findReportByAssignment_idAssignmentAndClas_idClassAndUser_idUser(idAssignment,idClass,idUser);
	}
	
	public String findMaxQuizGrade(int idUser,int idClass,int idQuiz){
		return reportRepository.findMaxQuizGrade(idUser,idClass,idQuiz);
	}
}

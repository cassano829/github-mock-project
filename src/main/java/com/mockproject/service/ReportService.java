package com.mockproject.service;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mockproject.model.Subject;
import com.mockproject.repository.QuestionRepository;
import com.mockproject.repository.ReportRepository;
import com.mockproject.model.Class;
import com.mockproject.model.Question;
import com.mockproject.model.Report;


@Service
public class ReportService {
	@Autowired
	private ReportRepository reportRepository;
	
	public List<Report> findReportByIdAssignmentAndIdClass(int idAssignment,int idClass){
		return reportRepository.findReportByIdAssignmentAndIdClass(idAssignment,idClass);
	}
	
	public List<Report> findDistinctByIdQuizAndIdClass(int idQuiz,int idClass){
		return reportRepository.findDistinctByIdQuizAndIdClass(idQuiz,idClass);
	}
	
	public List<Report> findReportByIdQuizAndIdClassAndIdUser(int idQuiz,int idClass,int idUser){
		return reportRepository.findReportByIdQuizAndIdClassAndIdUser(idQuiz,idClass,idUser);
	}
	
	public Report findReportByIdAssignmentAndIdClassAndIdUser(int idAssignment,int idClass,int idUser){
		return reportRepository.findReportByIdAssignmentAndIdClassAndIdUser(idAssignment,idClass,idUser);
	}
	
	public String findMaxQuizGrade(int idUser,int idClass,int idQuiz){
		return reportRepository.findMaxQuizGrade(idUser,idClass,idQuiz);
	}
}

package com.mockproject.service;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mockproject.model.Subject;
import com.mockproject.model.UserOfClass;
import com.mockproject.repository.QuizDetailRepository;
import com.mockproject.repository.QuizOfClassRepository;
import com.mockproject.repository.QuizOfUserRepository;
import com.mockproject.repository.UserOfClassRepository;
import com.mockproject.model.Class;
import com.mockproject.model.QuizDetail;
import com.mockproject.model.QuizOfClass;
import com.mockproject.model.QuizOfUser;


@Service
public class UserOfClassService {
	@Autowired
	private UserOfClassRepository userOfClassRepository;

	public List<UserOfClass> findUserOfClassByIdClass(int id){
		return userOfClassRepository.findUserOfClassByIdClass(id);
	}
}

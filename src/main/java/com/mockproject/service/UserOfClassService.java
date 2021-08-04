package com.mockproject.service;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mockproject.model.UserOfClass;
import com.mockproject.repository.UserOfClassRepository;


@Service
public class UserOfClassService {
	@Autowired
	private UserOfClassRepository userOfClassRepository;

	public List<UserOfClass> findUserOfClassByIdClass(int id){
		return userOfClassRepository.findUserOfClassByIdClass(id);
	}
}

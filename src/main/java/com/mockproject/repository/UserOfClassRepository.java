package com.mockproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mockproject.model.UserOfClass;


@Repository
public interface UserOfClassRepository extends JpaRepository<UserOfClass, Integer> {
	List<UserOfClass> findUserOfClassByClas_idClass(int id);
}

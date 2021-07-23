/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.repository;

import com.mockproject.model.QuizOfStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Asus
 */
@Repository
public interface QuizOfStudentRepository extends JpaRepository<QuizOfStudent,Integer>{
    
    @Query(value = "SELECT max(idQuizOfUser) FROM QuizesOfUser", nativeQuery = true)
    int getIdOfQuizOfUser();
}

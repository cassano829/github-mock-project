/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.repository;

import com.mockproject.model.QuizOfUser;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Asus
 */
@Repository
public interface QuizOfStudentRepository extends JpaRepository<QuizOfUser,Integer>{
    
    @Query(value = "SELECT max(idQuizOfUser) FROM QuizesOfUser", nativeQuery = true)
    int getIdOfQuizOfUser();
    
//    @Query("SELECT qs FROM QuizOfUser qs WHERE qs.idQuizOfUser=(SELECT max(qos.idQuizOfUser) FROM QuizOfUser qos JOIN qos.user u JOIN qos.quiz q WHERE u.idUser=?1 AND q.idQuiz=?2)")
//    QuizOfUser findByIdUserAndIdQuiz(int idUser,int idQuiz);
}

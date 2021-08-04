
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.repository;

import com.mockproject.model.QuizDetail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ACER
 */
@Repository
public interface QuizDetailRepository extends JpaRepository<QuizDetail, Integer> {

    @Query(value = "select * from QuizDetails where idQuizOfUser = ?1", nativeQuery = true)
    List<QuizDetail> findQuizDetailByQuizOfUser_idQuizOfUser(int id);

}

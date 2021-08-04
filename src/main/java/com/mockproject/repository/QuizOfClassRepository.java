
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.repository;

import com.mockproject.model.QuizOfClass;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Asus
 */
@Repository
public interface QuizOfClassRepository extends JpaRepository<QuizOfClass, Integer> {

    @Query(value = "select * from QuizesOfClass where idClass = ?1", nativeQuery = true)
    List<QuizOfClass> findQuizOfClassByIdClass(int id);

    QuizOfClass findByIdQuizAndIdClass(int idQuiz, int idClass);

    @Query(value = "SELECT idClass FROM QuizesOfClass WHERE idQuiz=?1", nativeQuery = true)
    List<Integer> findAllByIdQuiz(int idQuiz);

}

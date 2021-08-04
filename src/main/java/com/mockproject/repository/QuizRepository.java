
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mockproject.model.Quiz;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Asus
 */
@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {

    List<Quiz> findByIdUser(int idUser);

    @Query(value = "SELECT * FROM Quizes WHERE nameQuiz LIKE %?1% AND idQuiz IN (SELECT qc.idQuiz FROM QuizesOfClass qc WHERE qc.idClass=?2) ORDER BY idQuiz DESC",
            countQuery = "SELECT count(*) FROM Quizes WHERE nameQuiz LIKE %?1% AND idQuiz IN (SELECT qc.idQuiz FROM QuizesOfClass qc WHERE qc.idClass=?2)", nativeQuery = true)
    Page<Quiz> findAllByNameQuiz(String nameQuiz, int idClass, Pageable pageable);

    Quiz findQuizByIdQuiz(Integer idQuiz);

    @Query(value = "SELECT q.* FROM Quizes q WHERE q.nameQuiz LIKE %?1% AND q.idSubject=?2 AND q.idUser=?3 ORDER BY q.idQuiz DESC",
            countQuery = "SELECT count(*) FROM Quizes q WHERE q.nameQuiz LIKE %?1% AND q.idSubject=?2 AND q.idUser=?3", nativeQuery = true)
    Page<Quiz> getAllQuizByIdSubjectAndIdTeacherAndNameQuiz(String nameQuiz, String ibSubject, int idUser, Pageable pageable);

}

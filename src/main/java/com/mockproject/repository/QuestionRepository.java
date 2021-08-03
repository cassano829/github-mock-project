/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mockproject.model.Question;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
/**
 *
 * @author Asus
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer>{
    
    @Query(value="SELECT TOP(?1) q.* FROM Questions q WHERE idQuiz=?2 AND status=1 ORDER BY NEWID()",nativeQuery = true)
    public List<Question> findListQuestionByIdQuiz(int numOfQues,int idQuiz) ;
    
    @Query("SELECT q FROM Question q WHERE idQuiz=?1 AND status=1")
    Page<Question> findAllByIdQuiz(int idQuiz,Pageable pageable);
    
    @Query(value = "SELECT count(*) FROM Questions WHERE status=1 AND idQuiz=?1 ",nativeQuery = true)
    int countNumOfQuesOfQuiz(int idQuiz);
}

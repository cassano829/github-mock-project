package com.mockproject.repository;

import com.mockproject.model.QuizDetail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ACER
 */
@Repository
public interface QuizDetailRepository extends JpaRepository<QuizDetail, Integer> {
    
    List<QuizDetail> findQuizDetailByQuizOfUser_idQuizOfUser(int id);

}

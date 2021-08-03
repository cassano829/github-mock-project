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

//    @Query(value = "SELECT max(idQuiz) FROM Quizes", nativeQuery = true)
//    int getIdOfQuiz();
}

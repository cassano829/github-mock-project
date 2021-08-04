package com.mockproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mockproject.model.QuizOfUser;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface QuizOfUserRepository extends JpaRepository<QuizOfUser, Integer> {

    public List<QuizOfUser> findQuizOfUserByIdUserAndIdQuiz(int idUser, int idQuiz);

    @Query(value = "SELECT max(idQuizOfUser) FROM QuizesOfUser", nativeQuery = true)
    int getIdOfQuizOfUser();

    QuizOfUser findByIdQuizOfUser(int idQuizOfUser);

    @Query(value = "SELECT max(grade) FROM QuizOfUser qou WHERE qou.idUser=?1 and qou.idQuiz=?2")
    public Double findMaxQuizGrade(int idUser, int idQuiz);
}

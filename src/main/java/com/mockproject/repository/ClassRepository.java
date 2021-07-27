/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mockproject.model.Class;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Asus
 */
@Repository
public interface ClassRepository extends JpaRepository<Class, Integer>{
    
    List<Class> findByIdUser(int idUser);
    
    @Query("SELECT c FROM Class c JOIN c.subject s WHERE c.idUser=?1 AND s.idSubject=?2")
    List<Class> findByIdUserAndIdQuiz(int idUser,String idSubject);
    
}

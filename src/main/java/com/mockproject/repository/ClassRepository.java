/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mockproject.model.Class;
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
public interface ClassRepository extends JpaRepository<Class, Integer>{
    
//    List<Class> findByIdUser(int idTeacher);
    
    @Query("SELECT c FROM Class c WHERE c.idUser=?1 AND c.idSubject=?2")
    Page<Class> findByIdTeacherAndIdSubject(int idUser,String idSubject,Pageable pageable);
    
    @Query("SELECT c FROM Class c WHERE c.idClass in (SELECT uc.idClass FROM UserOfClass uc WHERE idUser=?1)")
    Page<Class> findAllByIdUser(int idStudent,Pageable pageable);
    
}

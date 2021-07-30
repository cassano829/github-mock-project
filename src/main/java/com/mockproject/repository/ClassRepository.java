/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.repository;

import com.mockproject.model.Class;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ACER
 */
@Repository
public interface ClassRepository extends JpaRepository<Class, Integer> {

    @Query("SELECT c FROM Class c WHERE c.idUser = :idUser AND c.idSubject = :idSubject")
    Page<Class> findAllClassWithTeacher(Pageable page, @Param("idUser") Integer idUser, @Param("idSubject") String idSubject);

    @Query("SELECT c FROM Class c WHERE c.nameClass like %:nameClass%")
    Page<Class> findClassWithName(Pageable page, @Param("nameClass") String name);

}

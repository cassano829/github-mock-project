/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.repository;

import com.mockproject.model.UsersOfClass;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ACER
 */
@Repository
public interface UsersOfClassRepository extends JpaRepository<UsersOfClass, Integer> {

    @Query("SELECT uol FROM UsersOfClass uol WHERE uol.idUser = :idUser")
    List<UsersOfClass> findUserByIdUser(@Param("idUser") Integer idUser);
}

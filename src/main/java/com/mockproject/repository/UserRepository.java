/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.repository;

import com.mockproject.model.Role;
import com.mockproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author ACER
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserByEmail(String email);

//    User findUserByVerificationCode(String code);

//    @Query("SELECT * " +
//            "FROM Users u JOIN RolesOfUser ru ON u.idUser = ru.idUser " +
//            "WHERE (u.email LIKE %:email% AND ru.idRole = :roleId AND u.status = :status)")
//    public List<User> search(@Param("email")String email, @Param("roleId")int roleId, @Param("status")boolean status);

    List<User> findAllByEmailContainsAndRolesAndStatus(String email, Role role, boolean status);

    User findUsersByIdUser(int idUser);
    
    @Query("SELECT u FROM User u WHERE u.verificationCode = ?1")
    public User findByVerificationCode(String code);
    
    User findUserByIdUser(int id);
    
}

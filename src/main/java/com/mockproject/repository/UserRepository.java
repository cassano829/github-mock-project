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

import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ACER
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserByEmail(String email);

    List<User> findAllByEmailContainsAndRolesAndStatus(String email, Role role, boolean status);

    User findUsersByIdUser(int idUser);

    @Query("SELECT u FROM User u WHERE u.verificationCode = ?1")
    public User findByVerificationCode(String code);

    User findUserByIdUser(int id);

    @Query("SELECT new com.mockproject.model.User(u.idUser,u.fullName,u.email) FROM User u "
            + "WHERE u.idUser IN (SELECT uoc.idUser FROM UserOfClass uoc WHERE uoc.idClass=?1)")
    List<User> findAllByidClass(int idClass);

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.repository;

import com.mockproject.model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ACER
 */
@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {

    @Query("SELECT n FROM News n WHERE n.status = 1 ORDER BY n.createDate DESC")
    Page<News> findAllNewsStatusOrderedDateDesc(Pageable page);

    @Query("SELECT n FROM News n ORDER BY n.createDate DESC")
    Page<News> findAllNewsOrderedDateDesc(Pageable page);
}

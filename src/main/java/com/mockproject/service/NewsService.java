/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.service;

/**
 *
 * @author ACER
 */
import com.mockproject.model.News;
import com.mockproject.repository.NewsRepository;
import com.mockproject.security.CustomUserDetail;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class NewsService {

    public final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    NewsRepository repo;

    public void save(News news) {
        CustomUserDetail user = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        news.setIdUser(user.getUser().getIdUser());
        news.setCreateDate(df.format(new Date()));
        repo.save(news);
    }

    public Page<News> getListNews(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 2);
        return repo.findAllNewsOrderedDateDesc(pageable);
    }

    public Page<News> getListNewsByStatus(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 2);
        return repo.findAllNewsStatusOrderedDateDesc(pageable);
    }

    public News getNewsById(int id) {
        return repo.getById(id);
    }

    public void delete(Integer id) {
        News news = getNewsById(id);
        news.setStatus(false);
        save(news);
    }
    
    public void restore(Integer id) {
        News news = getNewsById(id);
        news.setStatus(true);
        save(news);
    }

}

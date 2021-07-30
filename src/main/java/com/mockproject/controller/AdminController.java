/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.controller;

import com.mockproject.model.News;
import com.mockproject.model.User;
import com.mockproject.security.CustomUserDetail;
import com.mockproject.security.UserDetailServiceImp;
import com.mockproject.service.NewsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author ACER
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserDetailServiceImp service;

    @Autowired
    NewsService newService;

    @GetMapping("/deleteNews/{id}")
    public String deleteNews(@PathVariable(name = "id") Integer id) {
        newService.delete(id);
        return "redirect:/admin/home";
    }

    @GetMapping("/restoreNews/{id}")
    public String restoreNews(@PathVariable(name = "id") Integer id) {
        newService.restore(id);
        return "redirect:/admin/home";
    }

    @GetMapping("/editNews/{id}")
    public ModelAndView editNewsForm(@PathVariable(name = "id") Integer id) {
        ModelAndView mav = new ModelAndView("adminEditNews");
        News news = newService.getNewsById(id);
        mav.addObject("news", news);
        return mav;
    }

    @PostMapping("/editNews")
    public String editNews(@ModelAttribute("news") News news, Model model) {
        boolean checkEmpty = true;
        String error = "";
        if ("".trim().equals(news.getTitle())) {
            error += "Title cannot be blank! ";
            checkEmpty = false;
        }
        if ("".trim().equals(news.getInfor())) {
            error += "Content cannot be blank!";
            checkEmpty = false;
        }
        if (checkEmpty) {
            newService.save(news);
            model.addAttribute("message", "Update successful!");
        } else {
            model.addAttribute("msgError", error);
        }
        return "adminEditNews";
    }

    @PostMapping("/createNews")
    public String createNews(@ModelAttribute("news") News news, Model model) {
        boolean checkEmpty = true;
        String error = "";
        if ("".trim().equals(news.getTitle())) {
            error += "Title cannot be blank! ";
            checkEmpty = false;
        }
        if ("".trim().equals(news.getInfor())) {
            error += "Content cannot be blank!";
            checkEmpty = false;
        }
        if (checkEmpty) {
            news.setStatus(true);
            newService.save(news);
            return "redirect:/admin/home";
        } else {
            model.addAttribute("msgError", error);
            return "adminCreateNews";
        }
    }

    @GetMapping("/createNews")
    public String createNewsForm(Model model) {
        model.addAttribute("news", new News());
        return "adminCreateNews";
    }

    @GetMapping("/home")
    public String showHome(Model model) {
        return listByPageAdmin(model, 1);
    }

    @GetMapping("/pageAdmin/{pageNumber}")
    public String listByPageAdmin(Model model, @PathVariable(name = "pageNumber") Integer currentPage) {
        User u = new User();
        Page<News> page = newService.getListNews(currentPage);
        int totalPages = page.getTotalPages();
        List<News> list = page.getContent();
        for (News n : list) {
            u = service.getUserByIdUser(n.getIdUser());
        }
        if (list != null) {
            if (list.size() != 0) {
                model.addAttribute("currentPage", currentPage);
                model.addAttribute("totalPages", totalPages);
                model.addAttribute("nameUser", u.getFullName());
                model.addAttribute("listNews", list);
            } else {
                model.addAttribute("message", "Empty Announcement!");
            }
        }
        return "adminHome";
    }

    @GetMapping("/subject")
    public String showSubject() {
        return "adminSubject";
    }

    @GetMapping("/account")
    public String showAccount() {
        return "adminAccount";
    }

    @GetMapping("/user")
    public String showUsers() {
        return "adminUser";
    }
}

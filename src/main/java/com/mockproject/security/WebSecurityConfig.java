/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author ACER
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailServiceImp();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService());
        return authenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
//<<<<<<< HEAD
//                .antMatchers("/", "/student/create", "/student/save", "/student/verify").permitAll()
//                .antMatchers("/student/home", "/student/class", "/student/account").hasAuthority("STUDENT")
//                .antMatchers("/teacher/home", "/subject/teacher/*", "/teacher/account").hasAuthority("TEACHER")
//                .antMatchers("/admin/home", "/subject/admin/*", "/admin/account", "/admin/user").hasAuthority("ADMIN")
//=======
                .antMatchers("/", "/create", "/save", "/verify", "/handleException", "/loginError", "/page/**").permitAll()
                .and().authorizeRequests().antMatchers("/student/**").hasAuthority("STUDENT")
                .and().authorizeRequests().antMatchers("/admin/**").hasAuthority("ADMIN")
                .and().authorizeRequests().antMatchers("/teacher/**").hasAuthority("TEACHER")
                .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage("/")
                    .usernameParameter("txtEmail")
                    .passwordParameter("txtPassword")
                    .loginProcessingUrl("/login")
                    .successHandler(successHandler)
                    .failureHandler(failHandler)
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/403");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "**.png", "**.jpg", "/images/**");
    }

    @Autowired
    private LoginSuccessHandler successHandler;
    
    @Autowired
    private LoginFailHandler failHandler;
}

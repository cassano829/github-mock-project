/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mockproject.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 *
 * @author Asus
 */
@Entity
@Data
@Table(name = "Classes")
public class Class {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idClass;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idSubject")
    private Subject subject;
    
    private int idUser;
    
    private String nameClass;
    
    private boolean status;
    
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @Column(name = "createDate")
    private String createDate;
    
    private String password;
    
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude // không sử dụng trường này trong equals và hashcode
    @ToString.Exclude // Khoonhg sử dụng trong toString()
    @JoinTable(name = "QuizesOfClass", //Tạo ra một join Table tên là "QuizesOfClass"
            joinColumns = @JoinColumn(name = "idClass"),  // TRong đó, khóa ngoại chính là address_id trỏ tới class hiện tại (Address)
            inverseJoinColumns = @JoinColumn(name = "idQuiz") //Khóa ngoại thứ 2 trỏ tới thuộc tính ở dưới (Person)
    )
    private Set<Quiz> quizes;
    
    
}

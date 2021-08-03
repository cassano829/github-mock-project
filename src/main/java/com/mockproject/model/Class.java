package com.mockproject.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "Classes")
public class Class implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idClass;

    private String idSubject;

    private int idUser;

    private String nameClass;

    private boolean status;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @Column(name = "createDate")
    private String createDate;

    private String password;

}

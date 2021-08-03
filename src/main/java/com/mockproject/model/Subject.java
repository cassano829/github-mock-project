package com.mockproject.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Entity
@Data
@Table(name = "Subjects")
@SQLDelete(sql = "UPDATE Subjects SET status = 'false' WHERE idSubject=?")
@Where(clause = "status=true")
@FilterDef(name = "deletedSubjectFilter", parameters = @ParamDef(name = "status", type = "boolean"))
@Filter(name = "deletedSubjectFilter", condition = "status = :status")
public class Subject implements Serializable {

    @Id
    @Column(name = "idSubject", updatable = false, nullable = false, unique = true)
    @Pattern(regexp = "^([A-Z]{3})+([0-9]{3})$", message = "Incorrect Subject ID format - Ex : ABC123")
    private String idSubject;

    @Column(nullable = false)
    @NotBlank(message = "Subject name can't be empty")
    @NotEmpty(message = "Subject name can't be empty")
    private String nameSubject;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @Column(name = "createDate")
    private String createDate;

    private boolean status = Boolean.TRUE;

    @Column(name = "idUser")
    private int idUser;
}

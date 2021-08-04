
package com.mockproject.model;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author Asus
 */
@Entity
@Data
@Table(name = "QuizDetails")
public class QuizDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idQuizDetail;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idQuizOfUser")
    private QuizOfUser quizOfUser;
    
    private int userAnswer;
    
    private int idQuestion;
}

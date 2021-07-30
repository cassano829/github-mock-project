/* LiemNguyen created on 16/07/2021*/

package com.mockproject.repository;

import com.mockproject.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, String> {

    List<Subject> findByNameSubjectContains(String nameSubject);

    List<Subject> findAllByIdUser(int idUser);

    @Query(value = "SELECT * FROM Subjects s WHERE status =1 OR status = 0 ORDER BY s.status DESC",nativeQuery = true)
    List<Subject> findAll();

    @Query(value = "SELECT * FROM Subjects s WHERE idSubject LIKE ?1 AND (status =1 OR status = 0)",nativeQuery = true)
    Subject getByIdNonFilter(String idSubject);

}

/* LiemNguyen created on 16/07/2021*/

package com.mockproject.repository;

import com.mockproject.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, String> {

    List<Subject> findByNameSubjectContains(String nameSubject);

    List<Subject> findAllByIdUser(int idUser);
}

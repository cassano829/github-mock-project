package com.mockproject.repository;

import com.mockproject.model.Assignment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author truon
 */
public interface AssignmentRepository extends CrudRepository<Assignment, Integer>, PagingAndSortingRepository<Assignment, Integer> {

    public Page<Assignment> findAllByIdSubjectAndIdUser(String id, Integer idUser, Pageable pageable);

    public Page<Assignment> findByIdSubject(String idSubject, Pageable pageAble);

    @Query("from Assignment a where a.status = 1 and a.idAssignment in (select aoc.idAssignment from AssignmentsOfClass aoc where aoc.idClass = ?1)")
    public Page<Assignment> getListAssignmentOfClass(Integer idClass, Pageable pageable);

    Assignment findAssignmentByIdAssignment(int idClass);

}

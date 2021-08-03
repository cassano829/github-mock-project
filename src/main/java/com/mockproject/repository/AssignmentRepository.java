
package com.mockproject.repository;

import com.mockproject.model.Assignment;
import java.util.List;
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

//    @Query(value = "SELECT a FROM Assignment a WHERE a.status = 1 and a.idAssignment in (SELECT aoc.idAssignment FROM AssignmentsOfClass aoc WHERE aoc.idClass = ?1)")
//    public List<Assignment> getListClassesByIdAssignmentInside(Integer idClass);

    Assignment findAssignmentByIdAssignment(int idAssignment);

}

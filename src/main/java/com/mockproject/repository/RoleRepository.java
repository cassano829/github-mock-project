/* LiemNguyen created on 22/07/2021*/
package com.mockproject.repository;

import com.mockproject.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByNameEquals(String name);

}

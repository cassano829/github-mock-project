/* LiemNguyen created on 22/07/2021*/

package com.mockproject.service;

import com.mockproject.model.Role;
import com.mockproject.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    RoleRepository repo;


    public Optional<Role> findByName(String name) {
        return repo.findByNameEquals(name);
    }

    public Optional<Role> findById(int id) {
        return repo.findById(id);
    }

    public List<Role> getRoleList(){ return repo.findAll(); }
}

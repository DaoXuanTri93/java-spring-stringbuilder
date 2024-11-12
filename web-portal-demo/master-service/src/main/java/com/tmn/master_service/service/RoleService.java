package com.tmn.master_service.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmn.master_service.model.Role;
import com.tmn.master_service.repository.RoleRepository;

@Service
public class RoleService {
	@Autowired
    private RoleRepository roleRepository;

    public Iterable<Role> findAll() {
        return roleRepository.findAll();
    }

    public Role createRole(String roleName, String description) {
        Role newRole = new Role();
        newRole.setRoleName(roleName);
        newRole.setRoleDescription(description);
        newRole.setCreatedDate(new Date());
        
        return roleRepository.save(newRole);
    }
    @Transactional
    public void updateRole(Integer id, String roleName, String description) {
        Optional<Role> roleOptional = roleRepository.findById(id);
        
        if (roleOptional.isPresent()) {
            Role role = roleOptional.get();
            role.setRoleName(roleName);
            role.setRoleDescription(description);
            role.setUpdatedDate(new Date());
            roleRepository.save(role);
        } else {
            throw new RuntimeException("Role not found with id: " + id); 
        }
    }

    public void deleteById(Integer id) {
    	roleRepository.deleteById(id);
    }
    public List<Role> searchRoles(Integer id, String roleName) {
        return roleRepository.findByIdOrRoleName(id, roleName);
    }
}
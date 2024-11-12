package com.tmn.master_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tmn.master_service.model.Role;
import com.tmn.master_service.service.RoleService;

@RestController
@RequestMapping("/api/roles")
public class RolesController {
    @Autowired
    private RoleService roleService;

    @GetMapping
    public @ResponseBody Iterable<Role> getAllRole() {
        var roles = roleService.findAll();
        return roles;
    }

    @GetMapping("/search")
    public @ResponseBody List<Role> searchRoles(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String roleName) {
        return roleService.searchRoles(id, roleName);
    }
    
    @PostMapping("/create")
    public Role createRole(@RequestParam String roleName, @RequestParam String description) {
        return roleService.createRole(roleName, description);
    }
    
    @PutMapping("/update")
    public ResponseEntity<String> updateRole(
            @RequestParam Integer id,
            @RequestParam String roleName,
            @RequestParam String description) {
        try {
            roleService.updateRole(id, roleName, description);
            return ResponseEntity.ok("Role updated successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update role.");
        }
    }

    @DeleteMapping("{id}")
    public @ResponseBody void deleteUser(@PathVariable Integer id) {
        roleService.deleteById(id);
    }
}
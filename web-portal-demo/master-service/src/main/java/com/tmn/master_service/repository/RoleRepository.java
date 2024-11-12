package com.tmn.master_service.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.tmn.master_service.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	@Query("SELECT r FROM Role r WHERE (:id IS NULL OR r.id = :id) AND (:roleName IS NULL OR r.roleName = :roleName)")
	List<Role> findByIdOrRoleName(@Param("id") Integer id, @Param("roleName") String roleName);
}
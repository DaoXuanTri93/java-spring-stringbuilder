package com.tmn.master_service.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tmn.master_service.dto.UserDto;
import com.tmn.master_service.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findById(int id);
	Optional<User> findByEmail(String email);
 
  @Query("SELECT new com.tmn.master_service.dto.UserDto(u.id, u.userName, u.name, u.role.id, u.role.roleName, u.team.id, u.team.teamName, u.description, u.createdBy, u.createdDate, u.updatedBy, u.updatedDate) FROM User u " +
           "WHERE (:userName IS NULL OR u.userName LIKE %:userName%) " +
           "AND (:userId IS NULL OR u.id = :userId) " +
           "AND (:roleId IS NULL OR u.roleId = :roleId) " +
           "AND (:teamId IS NULL OR u.teamId = :teamId) " +
           "ORDER BY u.userName")
    Page<UserDto> findByFilters(@Param("userName") String userName, 
                             @Param("userId") Integer userId,
                             @Param("roleId") Integer roleId,
                             @Param("teamId") Long teamId,
                             Pageable pageable);
}

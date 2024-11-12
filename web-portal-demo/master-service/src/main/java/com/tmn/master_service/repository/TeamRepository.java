package com.tmn.master_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tmn.master_service.model.Team;
import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long>, JpaSpecificationExecutor<Team> {

    List<Team> findByFlagIs(boolean flag);

    Page<Team> findAllByFlag(boolean flag, Pageable pageable);

    Page<Team> findAllByFlagAndTeamNameContaining(boolean flag, Pageable pageable, String search);

    @Query("SELECT t FROM Team t WHERE t.flag = false AND (:id IS NULL OR t.teamId = :id) AND (:name IS NULL OR t.teamName LIKE CONCAT('%',:name,'%'))")
    Page<Team> findAllTeamIdAndTeamNameNameLike(@Param("id") Long id, @Param("name") String name, Pageable pageable);

}

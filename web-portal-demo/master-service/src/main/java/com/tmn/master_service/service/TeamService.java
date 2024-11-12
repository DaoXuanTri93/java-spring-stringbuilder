package com.tmn.master_service.service;

import com.tmn.master_service.dto.TeamDto;

import org.springframework.data.domain.Page;

public interface TeamService {

    TeamDto createTeam(TeamDto teamDto);

    TeamDto updateTeam(int id, TeamDto teamDto);

    TeamDto findTeamById(long id);

    void deleteTeam(int id);

    Page<TeamDto> getAllPage(String searchTeamName, Long searchTeamId, int pageNo, int pageSize);

}

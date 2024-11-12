package com.tmn.master_service.dto.mapper;

import com.tmn.master_service.dto.TeamDto;
import com.tmn.master_service.model.Team;

public class TeamMapper {

    private TeamMapper(){}
    public static TeamDto mapToTeamDto(Team team) {
        return new TeamDto(
            team.getTeamId(),
            team.getTeamName(),
            team.getDescription(),
            team.getCreateBy(),
            team.getUpdateBy(),
            team.getCreateDate(),
            team.getUpdateDate(),
            team.isFlag()
        );

    }

    public static Team mapToteam(TeamDto teamDto) {
        return new Team(
            teamDto.getTeamId(),
            teamDto.getTeamName(),
            teamDto.getDescription(),
            teamDto.getCreateBy(),
            teamDto.getUpdateBy(),
            teamDto.getCreateDate(),
            teamDto.getUpdateDate(),
            teamDto.isFlag()
                );

    }
}

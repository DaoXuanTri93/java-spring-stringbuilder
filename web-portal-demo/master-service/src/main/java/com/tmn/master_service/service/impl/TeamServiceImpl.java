package com.tmn.master_service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tmn.master_service.dto.TeamDto;
import com.tmn.master_service.dto.mapper.TeamMapper;
import com.tmn.master_service.model.Team;
import com.tmn.master_service.repository.TeamRepository;
import com.tmn.master_service.service.TeamService;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Override
    public TeamDto createTeam(TeamDto teamDto) {
        teamDto.setFlag(false);
        teamDto.setCreateDate(new Date());
        teamDto.setUpdateDate(new Date());
        teamDto.setCreateBy("ADMIN");
        teamDto.setUpdateBy("ADMIN");
        Team team = TeamMapper.mapToteam(teamDto);
        Team team2 = teamRepository.save(team);
        return TeamMapper.mapToTeamDto(team2);
    }

    @Override
    public TeamDto findTeamById(long id) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new RuntimeException("Team not exists"));
        return TeamMapper.mapToTeamDto(team);
    }

    @Override
    public TeamDto updateTeam(int id, TeamDto teamDto) {
        // check id has exists
        TeamDto teamDtoNew = findTeamById(id);
        teamDto.setTeamId(teamDtoNew.getTeamId());
        teamDto.setCreateBy(teamDtoNew.getCreateBy());
        teamDto.setUpdateBy("ADMIN-UPDATE");
        teamDto.setUpdateDate(new Date());
        teamDto.setCreateDate(teamDtoNew.getCreateDate());
        Team team = TeamMapper.mapToteam(teamDto);
        teamRepository.save(team);
        return teamDto;
    }

    @Override
    public void deleteTeam(int id) {
        TeamDto teamDto = findTeamById(id);
        teamDto.setFlag(true);
        teamRepository.save(TeamMapper.mapToteam(teamDto));
    }

    @Override
    public Page<TeamDto> getAllPage(String searchTeamName, Long searchTeamId, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by("teamId").descending());

        Page<TeamDto> pageP;
        if (searchTeamName == null && searchTeamId == null) {
            pageP = teamRepository.findAllByFlag(false, pageable).map(TeamMapper::mapToTeamDto);
        } else {
            pageP = teamRepository
                    .findAllTeamIdAndTeamNameNameLike(searchTeamId, searchTeamName, pageable)
                    .map(TeamMapper::mapToTeamDto);
        }
        return pageP;

    }

}

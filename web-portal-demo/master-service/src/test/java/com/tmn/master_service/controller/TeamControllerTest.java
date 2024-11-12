package com.tmn.master_service.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.tmn.master_service.dto.TeamDto;
import com.tmn.master_service.dto.response.Objectpage;
import com.tmn.master_service.service.TeamService;

public class TeamControllerTest {

    @Mock
    private TeamService teamService;

    @InjectMocks
    private TeamController teamController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllPages() {
        // 1. create mock data
        Page<TeamDto> mockPage = new PageImpl<>(Arrays.asList(new TeamDto(), new TeamDto()));
        // 2. define behavior of Repository
        when(teamService.getAllPage(any(), any(), anyInt(), anyInt())).thenReturn(mockPage);
        // 3. call service method
        Objectpage<TeamDto> response = teamController.getAllPages(null, null, 1, 10);

        // 4. assert the result
        // 4.1 ensure repository is called
        verify(teamService, times(1)).getAllPage(any(), any(), anyInt(), anyInt());

        assertEquals(2, response.getTotalLength());
        assertEquals(2, response.getTotalSize());
    }

    @Test
    public void testCreateTeam() {
        TeamDto teamDto = new TeamDto();
        when(teamService.createTeam(any(TeamDto.class))).thenReturn(teamDto);

        ResponseEntity<TeamDto> response = teamController.createTeam(teamDto);

        verify(teamService, times(1)).createTeam(any(TeamDto.class));
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetTeamById() {
        TeamDto teamDto = new TeamDto(
                1L,
                "demo",
                "demo",
                "demo",
                "demo",
                new Date(),
                new Date(),
                false);
        when(teamService.findTeamById(anyInt())).thenReturn(teamDto);

        ResponseEntity<TeamDto> response = teamController.getTeamById(1);

        verify(teamService, times(1)).findTeamById(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // check not null
        // assertNotNull(response.getBody());
    }

    @Test
    public void testUpdateTeam() {
        TeamDto teamDto = new TeamDto();
        when(teamService.updateTeam(anyInt(), any(TeamDto.class))).thenReturn(teamDto);

        ResponseEntity<TeamDto> response = teamController.updateTeam(1, teamDto);

        verify(teamService, times(1)).updateTeam(anyInt(), any(TeamDto.class));
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testDeleteTeam() {
        doNothing().when(teamService).deleteTeam(anyInt());

        ResponseEntity<Void> response = teamController.deleteTeam(1);

        verify(teamService, times(1)).deleteTeam(anyInt());
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }

    @Test
    public void testGetAllPagesWithSearchParams() {
        Page<TeamDto> mockPage = new PageImpl<>(Arrays.asList(new TeamDto()));
        when(teamService.getAllPage(eq("teamName"), eq(1L), anyInt(), anyInt())).thenReturn(mockPage);

        Objectpage<TeamDto> response = teamController.getAllPages("teamName", 1L, 1, 10);

        verify(teamService, times(1)).getAllPage(eq("teamName"), eq(1L), anyInt(), anyInt());
        assertEquals(1, response.getTotalLength());
        assertEquals(1, response.getTotalSize());
    }
}
package com.tmn.master_service.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.tmn.master_service.dto.TeamDto;
import com.tmn.master_service.model.Team;
import com.tmn.master_service.repository.TeamRepository;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class TeamServiceTest {

    @Autowired
    TeamService teamService;

    @MockBean
    private TeamRepository teamRepository;

    private TeamDto teamRequest;
    private Team teamEntity;

    @BeforeEach
    public void initData() {
        teamRequest = new TeamDto(
                515L,
                "demo",
                "demo",
                "demo",
                "demo",
                new Date(),
                new Date(),
                false);
        teamEntity = new Team(
                515L,
                "demo",
                "demo",
                "demo",
                "demo",
                new Date(),
                new Date(),
                false);

    }

    @Test
    public void createTeam_service_success() {
        initData();
        when(teamRepository.save(any())).thenReturn(teamEntity);

        var response = teamService.createTeam(teamRequest);
        Assertions.assertThat(response.getTeamName()).isEqualTo("demo");
        Assertions.assertThat(response.getDescription()).isEqualTo("demo");
    }
}

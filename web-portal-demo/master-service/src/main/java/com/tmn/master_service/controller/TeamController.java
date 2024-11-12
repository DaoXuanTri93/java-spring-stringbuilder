package com.tmn.master_service.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.tmn.master_service.dto.TeamDto;
import com.tmn.master_service.dto.response.Objectpage;
import com.tmn.master_service.service.TeamService;

@RestController
@RequestMapping("api/teams")
// @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:8082"})
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping("/allpage")
    public Objectpage<TeamDto> getAllPages(
            @RequestParam(required = false) String searchTeamName,
            @RequestParam(required = false) Long searchTeamId,
            @RequestParam(defaultValue = "1", required = false) int pageNumber,
            @RequestParam(defaultValue = "10", required = false) int pageSize) {

        Page<TeamDto> pageTeam = teamService.getAllPage(searchTeamName, searchTeamId, pageNumber, pageSize);
        long totalLenght = pageTeam.getTotalElements();
        int totalSize = pageTeam.getSize();
        List<TeamDto> listP = pageTeam.getContent();
        return new Objectpage<>(totalLenght, totalSize, listP);
    }

    @PostMapping
    public ResponseEntity<TeamDto> createTeam(@RequestBody @Valid TeamDto teamDto) {
        return new ResponseEntity<>(teamService.createTeam(teamDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamDto> getTeamById(@PathVariable int id) {

        return new ResponseEntity<>(teamService.findTeamById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamDto> updateTeam(@PathVariable int id, @RequestBody @Valid TeamDto teamDto) {
        return new ResponseEntity<>(teamService.updateTeam(id, teamDto), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable int id) {
        teamService.deleteTeam(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}

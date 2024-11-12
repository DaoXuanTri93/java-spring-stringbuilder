package com.tmn.master_service.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.tmn.master_service.dto.SelectItem;
import com.tmn.master_service.dto.UserDto;
import com.tmn.master_service.model.User;
import com.tmn.master_service.repository.RoleRepository;
import com.tmn.master_service.repository.TeamRepository;
import com.tmn.master_service.repository.UserRepository;

@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TeamRepository teamRepository;

  @Autowired
  private RoleRepository roleRepository;

  public Iterable<User> findAll() {
    return userRepository.findAll();
  }

  public User save(User user) {
    return userRepository.save(user);
  }

  public void deleteById(Integer id) {
    userRepository.deleteById(id);
  }

  public Optional<User> findById(Integer id) {
    return userRepository.findById(id);
  }

  public List<SelectItem> getTeams() {
    return teamRepository.findAll().stream()
        .map(team -> {
          SelectItem item = new SelectItem();
          item.setLabel(team.getTeamName());
          item.setValue(Math.toIntExact(team.getTeamId()));
          return item;
        })
        .sorted(Comparator.comparing(SelectItem::getLabel))
        .collect(Collectors.toList());
  }

  public List<SelectItem> getRoles() {
    return roleRepository.findAll().stream()
        .map(role -> {
          SelectItem item = new SelectItem();
          item.setLabel(role.getRoleName());
          item.setValue(role.getId());
          return item;
        })
        .sorted(Comparator.comparing(SelectItem::getLabel))
        .collect(Collectors.toList());
  }

  public Page<UserDto> getUsers(int page, int size, String userName, Integer userId, Integer roleId, Long teamId) {
    return userRepository.findByFilters(userName, userId, roleId, teamId, PageRequest.of(page, size));
}
}

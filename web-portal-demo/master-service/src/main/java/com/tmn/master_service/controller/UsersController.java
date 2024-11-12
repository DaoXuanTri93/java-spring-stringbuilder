package com.tmn.master_service.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tmn.master_service.dto.UserCreateDto;
import com.tmn.master_service.dto.SelectItem;
import com.tmn.master_service.dto.UserUpdateDto;
import com.tmn.master_service.dto.UserDto;
import com.tmn.master_service.model.User;
import com.tmn.master_service.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UsersController {
  @Autowired
  private UserService userService;

  @Autowired
  private ModelMapper modelMapper;

  @PostMapping()
  public @ResponseBody User addNewUser(@RequestBody UserCreateDto createUserDto) {
    var user = modelMapper.map(createUserDto, User.class);
    var createdUser = userService.save(user);
    return createdUser;
  }

  @GetMapping
    public Map<String, Object> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) Integer roleId,
            @RequestParam(required = false) Long teamId) {
        Page<UserDto> usersPage = userService.getUsers(page, size, userName, userId, roleId, teamId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("users", usersPage.getContent());
        response.put("totalElements", usersPage.getTotalElements());
        response.put("totalPages", usersPage.getTotalPages());
        
        return response;
    }

  @GetMapping("select-items")
  public @ResponseBody Map<String, List<SelectItem>> getSelectItems() {
    Map<String, List<SelectItem>> response = new HashMap<>();
    response.put("teams", userService.getTeams());
    response.put("roles", userService.getRoles());
    return response;
  }

  @GetMapping("{id}")
  public @ResponseBody Optional<User> getUser(@PathVariable Integer id) {
    return userService.findById(id);
  }

  @DeleteMapping("{id}")
  public @ResponseBody void deleteUser(@PathVariable Integer id) {
    userService.deleteById(id);
  }

  @PutMapping("{id}")
  public @ResponseBody User updateUser(@PathVariable Integer id, @RequestBody UserUpdateDto updateUserDto) {
    Optional<User> optionalUser = userService.findById(id);

    if (optionalUser.isPresent()) {
      User existingUser = optionalUser.get();
      modelMapper.map(updateUserDto, existingUser);
      return userService.save(existingUser);
    } else {
      throw new RuntimeException("User not found with id " + id);
    }
  }
}

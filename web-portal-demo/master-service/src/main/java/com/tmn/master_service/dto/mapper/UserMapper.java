package com.tmn.master_service.dto.mapper;

import org.modelmapper.PropertyMap;

import com.tmn.master_service.dto.UserDto;
import com.tmn.master_service.model.User;

public class UserMapper extends PropertyMap<User, UserDto> {
  @Override
  protected void configure() {
      map().setRoleId(source.getRoleId());
  }
}
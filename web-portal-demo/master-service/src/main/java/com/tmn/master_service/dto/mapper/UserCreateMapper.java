package com.tmn.master_service.dto.mapper;

import java.util.Date;

import org.modelmapper.PropertyMap;

import com.tmn.master_service.dto.UserCreateDto;
import com.tmn.master_service.model.User;

public class UserCreateMapper extends PropertyMap<UserCreateDto, User> {
  @Override
  protected void configure() {
      map().setCreatedBy("Admin");
      map().setCreatedDate(new Date());
      map().setId(null);
  }
}
package com.tmn.master_service.dto.mapper;

import java.util.Date;

import org.modelmapper.PropertyMap;

import com.tmn.master_service.dto.UserUpdateDto;
import com.tmn.master_service.model.User;

public class UserUpdateMapper extends PropertyMap<UserUpdateDto, User> {
  @Override
  protected void configure() {
      map().setUpdatedBy("Admin");
      map().setUpdatedDate(new Date());
  }
}
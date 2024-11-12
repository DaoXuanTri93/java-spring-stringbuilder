package com.tmn.master_service.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateDto {
  
	private String userName;

	private String name;

 	private Long teamId;

 	private Integer roleId;

	private String description;
}

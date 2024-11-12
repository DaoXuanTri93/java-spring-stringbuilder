package com.tmn.master_service.dto;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
  public UserDto(Integer id, String userName, String name, Integer roleId, String roleName, Long teamId,
      String teamName, String description, String createdBy, Date createdDate, String updatedBy, Date updatedDate) {
    this.id = id;
    this.userName = userName;
    this.name = name;
    this.roleId = roleId;
    this.roleName = roleName;
    this.teamId = teamId;
    this.teamName = teamName;
    this.description = description;
    this.createdBy = createdBy;
    this.createdDate = createdDate;
    this.updatedBy = updatedBy;
    this.updatedDate = updatedDate;
  }

  private Integer id;

  private String userName;

  private String name;

  private Long teamId;

  private String teamName;

  private Integer roleId;

  private String roleName;

  private String description;

  private String createdBy;

  @JsonFormat(pattern = "yyyy/MM/dd")
  private Date createdDate;

  private String updatedBy;

  @JsonFormat(pattern = "yyyy/MM/dd")
  private Date updatedDate;
}

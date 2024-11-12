package com.tmn.master_service.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private String userName;

	private String name;

	private String email;

  @ManyToOne
  @JoinColumn(name = "teamId", insertable = false, updatable = false)
  private Team team;

	private Long teamId;

  @ManyToOne
  @JoinColumn(name = "roleId", insertable = false, updatable = false)
  private Role role;

	private Integer roleId;

	private String description;

	private String createdBy;
  
  @JsonFormat(pattern = "yyyy/MM/dd")  
	private Date createdDate;

	private String updatedBy;
  
  @JsonFormat(pattern = "yyyy/MM/dd")  
	private Date updatedDate;
}


package com.tmn.master_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "teams")
@Entity
public class Team {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long teamId;

	@Column
	private String teamName;

	@Column
	private String description;

	@Column
	private String createBy;

	@Column
	private String updateBy;

	@Column
	private Date createDate;

	@Column
	private Date updateDate;

	@Column
	private boolean flag;

}

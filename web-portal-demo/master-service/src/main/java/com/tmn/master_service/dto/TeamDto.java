package com.tmn.master_service.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeamDto {

    private Long teamId;

    @NotBlank(message = "This field is required")
    @NotNull(message = "This field is required")
    @Length(min = 3, max = 255, message = "Length must be between 3 and 255 characters")
    private String teamName;

    @Length(max = 255, message = "Maximum input characters is 255")
    private String description;

    private String createBy;

    private String updateBy;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")   
    private Date createDate;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")   
    private Date updateDate;

    private boolean flag;
}

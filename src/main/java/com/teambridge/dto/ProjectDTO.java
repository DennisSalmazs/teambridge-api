package com.teambridge.dto;

import com.teambridge.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {

    private Long id; // for project - task mapping

    @NotBlank(message = "Project Name is a required field")
    private String projectName;

    @NotBlank(message = "Project Code is a required field")
    private String projectCode;

    @NotNull(message = "Please select a manager")
    private UserDTO assignedManager;

    @NotNull(message = "Please select a Start Date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "Please select an End Date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @NotBlank(message = "Project Detail is a required field")
    private String projectDetail;

    private Status projectStatus;

    private int completeTaskCounts;
    private int unfinishedTaskCounts;

}

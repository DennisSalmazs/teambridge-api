package com.teambridge.repository;

import com.teambridge.entity.Project;
import com.teambridge.entity.User;
import com.teambridge.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    Project findByProjectCode(String projectCode);

    List<Project> findAllByAssignedManager(User manager);

    List<Project> findAllByProjectStatusIsNotAndAssignedManager(Status status, User manager);
}

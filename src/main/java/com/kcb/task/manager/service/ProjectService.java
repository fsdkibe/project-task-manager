package com.kcb.task.manager.service;

import com.kcb.task.manager.dto.ProjectSummaryDto;
import com.kcb.task.manager.model.Project;

import java.util.List;

public interface ProjectService {

    List<Project> getAllProjects();

    Project getProjectById(Long id);

    Project createProject(Project project);

    Project updateProject(Long id, Project projectDetails);

    void deleteProject(Long id);

    public List<ProjectSummaryDto> getProjectSummary();
}
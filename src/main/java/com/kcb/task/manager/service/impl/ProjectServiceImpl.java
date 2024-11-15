package com.kcb.task.manager.service.impl;

import com.kcb.task.manager.dto.ProjectSummaryDto;
import com.kcb.task.manager.model.Project;
import com.kcb.task.manager.repository.ProjectRepository;
import com.kcb.task.manager.repository.TaskRepository;
import com.kcb.task.manager.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    private TaskRepository taskRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Project getProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
    }

    @Override
    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Project updateProject(Long id, Project projectDetails) {
        Project existingProject = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));

        existingProject.setName(projectDetails.getName());
        existingProject.setDescription(projectDetails.getDescription());

        return projectRepository.save(existingProject);
    }

    @Override
    public void deleteProject(Long id) {
        Project existingProject = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));

        projectRepository.delete(existingProject);
    }

    @Override
    public List<ProjectSummaryDto> getProjectSummary() {
        List<Project> projects = projectRepository.findAll(); // Retrieve all projects
        return projects.stream()
                .map(project -> {
                    Map<String, Long> taskCounts = taskRepository.countTasksByStatus(project.getId());
                    return new ProjectSummaryDto(project.getId(), project.getName(), taskCounts);
                })
                .collect(Collectors.toList());
    }

}
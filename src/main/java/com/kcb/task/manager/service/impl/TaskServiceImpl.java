package com.kcb.task.manager.service.impl;

import com.kcb.task.manager.dto.TaskDto;
import com.kcb.task.manager.model.Project;
import com.kcb.task.manager.model.Task;
import com.kcb.task.manager.repository.ProjectRepository;
import com.kcb.task.manager.repository.TaskRepository;
import com.kcb.task.manager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public TaskDto addTaskToProject(Long projectId, TaskDto taskDto) {
        Task task = new Task();
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project with ID " + projectId + " not found."));
        task.setProject(project);

        return setTask(taskDto, task);
    }

    @Override
    public List<Task> getTasksForProject(Long projectId, String status, String dueDate) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project with ID " + projectId + " not found."));

        List<Task> tasks = taskRepository.findByProject(project);

        if (status != null) {
            tasks = tasks.stream()
                    .filter(task -> task.getStatus().toString().equalsIgnoreCase(status))
                    .collect(Collectors.toList());
        }

        if (dueDate != null) {
            LocalDate filterDate = LocalDate.parse(dueDate);
            tasks = tasks.stream()
                    .filter(task -> !task.getDueDate().isAfter(filterDate))
                    .collect(Collectors.toList());
        }

        return tasks;
    }

    @Override
    public TaskDto updateTask(Long taskId, TaskDto updatedTask) {
        Task existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task with ID " + taskId + " not found."));

       return setTask(updatedTask, existingTask);

    }

    @Override
    public void deleteTask(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task with ID " + taskId + " not found."));
        taskRepository.delete(task);
    }
    private TaskDto setTask(TaskDto taskDto, Task task) {
        task.setStatus(taskDto.getStatus());
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setDueDate(taskDto.getDueDate());
        taskRepository.save(task);
        return taskDto;
    }
}

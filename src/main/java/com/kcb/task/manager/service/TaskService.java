package com.kcb.task.manager.service;

import com.kcb.task.manager.dto.TaskDto;
import com.kcb.task.manager.model.Task;

import java.util.List;

public interface TaskService {

    TaskDto addTaskToProject(Long projectId, TaskDto taskDto);

    List<Task> getTasksForProject(Long projectId, String status, String dueDate);

    TaskDto updateTask(Long taskId, TaskDto updatedTask);

    void deleteTask(Long taskId);
}

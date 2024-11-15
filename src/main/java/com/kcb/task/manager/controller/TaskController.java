package com.kcb.task.manager.controller;

import com.kcb.task.manager.dto.TaskDto;
import com.kcb.task.manager.model.Task;
import com.kcb.task.manager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/projects/{projectId}")
    public ResponseEntity<TaskDto> addTaskToProject(@PathVariable Long projectId, @RequestBody TaskDto taskDto) {
        return ResponseEntity.ok(taskService.addTaskToProject(projectId, taskDto));
    }

    @GetMapping("/projects/{projectId}")
    public ResponseEntity<List<Task>> getTasksForProject(
            @PathVariable Long projectId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String dueDate) {
        return ResponseEntity.ok(taskService.getTasksForProject(projectId, status, dueDate));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Long taskId, @RequestBody TaskDto updatedTask) {
        return ResponseEntity.ok(taskService.updateTask(taskId, updatedTask));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.ok("Task deleted successfully.");
    }
}

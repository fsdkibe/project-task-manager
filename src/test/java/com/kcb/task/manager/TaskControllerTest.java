package com.kcb.task.manager;

import com.kcb.task.manager.controller.TaskController;
import com.kcb.task.manager.dto.TaskDto;
import com.kcb.task.manager.model.Task;
import com.kcb.task.manager.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {

    @InjectMocks
    private TaskController taskController;

    @Mock
    private TaskService taskService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }

    @Test
    public void testAddTaskToProject() throws Exception {
        TaskDto taskDto = new TaskDto();
        taskDto.setTitle("Menu update task");

        TaskDto createdTaskDto = new TaskDto();
        createdTaskDto.setProjectId(1L);
        createdTaskDto.setTitle("Menu update task");

        when(taskService.addTaskToProject(1L, taskDto)).thenReturn(createdTaskDto);

        mockMvc.perform(post("/tasks/projects/1")
                        .contentType("application/json")
                        .content("{\"title\": \"Menu update task\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.projectId").value(1))
                .andExpect(jsonPath("$.title").value("Menu update task"));

        verify(taskService, times(1)).addTaskToProject(1L, taskDto);
    }

    @Test
    public void testGetTasksForProject() throws Exception {
        Task task1 = new Task();
        task1.setId(1L);
        task1.setTitle("Task 1");

        Task task2 = new Task();
        task2.setId(2L);
        task2.setTitle("Task 2");

        List<Task> tasks = Arrays.asList(task1, task2);

        when(taskService.getTasksForProject(1L, null, null)).thenReturn(tasks);

        mockMvc.perform(get("/tasks/projects/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].projectId").value(1))
                .andExpect(jsonPath("$[1].projectId").value(2))
                .andExpect(jsonPath("$[0].title").value("Task 1"))
                .andExpect(jsonPath("$[1].title").value("Task 2"));

        verify(taskService, times(1)).getTasksForProject(1L, null, null);
    }

    @Test
    public void testUpdateTask() throws Exception {
        TaskDto updatedTask = new TaskDto();
        updatedTask.setTitle("Updated Task");

        TaskDto taskDto = new TaskDto();
        taskDto.setProjectId(1L);
        taskDto.setTitle("Updated Task");

        when(taskService.updateTask(1L, updatedTask)).thenReturn(taskDto);

        mockMvc.perform(put("/tasks/1")
                        .contentType("application/json")
                        .content("{\"title\": \"Updated Task\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.projectId").value(1))
                .andExpect(jsonPath("$.title").value("Updated Task"));

        verify(taskService, times(1)).updateTask(1L, updatedTask);
    }

    @Test
    public void testDeleteTask() throws Exception {
        doNothing().when(taskService).deleteTask(1L);

        mockMvc.perform(delete("/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Task deleted successfully."));

        verify(taskService, times(1)).deleteTask(1L);
    }
}

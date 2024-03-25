package com.excite.taskmanager.unitTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.excite.taskmanager.domain.exception.TaskNotExistException;
import com.excite.taskmanager.domain.object.TaskObject;
import com.excite.taskmanager.domain.repository.TaskRepository;
import com.excite.taskmanager.domain.service.TaskService;

@SpringBootTest
public class TaskServiceTest {
    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetTasks() throws Exception {
        TaskObject task1 = new TaskObject();
        TaskObject task2 = new TaskObject();
        when(taskRepository.getTasks()).thenReturn(Arrays.asList(task1, task2));

        List<TaskObject> tasks = taskService.getTasks();

        assertEquals(2, tasks.size());
    }

    @Test
    public void testGetTaskById_Success() throws TaskNotExistException {
        TaskObject task = new TaskObject();
        when(taskRepository.getTaskById(anyInt())).thenReturn(task);

        TaskObject ret = taskService.getTaskById(1);

        assertNotNull(ret);
    }

    @Test
    public void testGetTaskById_NotExist() {
        when(taskRepository.getTaskById(anyInt())).thenReturn(null);

        assertThrows(TaskNotExistException.class, () -> {
            taskService.getTaskById(1);
        });
    }

    @Test
    public void testCreateTask() {
        TaskObject task = new TaskObject();
        taskService.createTask(task);
        verify(taskRepository, times(1)).createTask(task);
    }

    @Test
    public void testUpdateTask_Success() throws TaskNotExistException {
        TaskObject task = new TaskObject();
        when(taskRepository.updateTask(task)).thenReturn(1);
        taskService.updateTask(task);
        verify(taskRepository, times(1)).updateTask(task);
    }

    @Test
    public void testUpdateTask_NotExist() {
        when(taskRepository.updateTask(any(TaskObject.class))).thenReturn(0);

        assertThrows(TaskNotExistException.class, () -> {
            taskService.updateTask(new TaskObject());
        });
    }

    @Test
    public void testDeleteTask_Success() throws TaskNotExistException {
        when(taskRepository.deleteTask(anyInt())).thenReturn(1);
        taskService.deleteTask(1);
        verify(taskRepository, times(1)).deleteTask(1);
    }

    @Test
    public void testDeleteTask_NotExist() {
        when(taskRepository.deleteTask(anyInt())).thenReturn(0);

        assertThrows(TaskNotExistException.class, () -> {
            taskService.deleteTask(1);
        });
    }
}

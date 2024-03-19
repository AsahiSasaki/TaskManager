package com.excite.taskmanager.unitTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.boot.test.context.SpringBootTest;

import com.excite.taskmanager.domain.object.TaskObject;
import com.excite.taskmanager.infrastructure.entity.Task;
import com.excite.taskmanager.infrastructure.mapper.TaskMapper;
import com.excite.taskmanager.infrastructure.repository.TaskRepositoryImpl;

@SpringBootTest
public class TaskRepositoryTest {
    @InjectMocks
    private TaskRepositoryImpl taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetTasks() {
        Task task = new Task();
        when(taskMapper.selectByExample(null)).thenReturn(Arrays.asList(task));
        when(modelMapper.map(Arrays.asList(task), new TypeToken<List<TaskObject>>() {
        }.getType())).thenReturn(Arrays.asList(new TaskObject()));
        List<TaskObject> result = taskRepository.getTasks();
        assertNotNull(result);
        verify(taskMapper, times(1)).selectByExample(null);
    }

    @Test
    public void testGetTaskById() {
        Task task = new Task();
        when(taskMapper.selectByPrimaryKey(anyInt())).thenReturn(task);
        when(modelMapper.map(task, TaskObject.class)).thenReturn(new TaskObject());
        TaskObject result = taskRepository.getTaskById(1);
        assertNotNull(result);
        verify(taskMapper, times(1)).selectByPrimaryKey(anyInt());
    }

    @Test
    public void testCreateTask() {
        TaskObject taskObject = new TaskObject();
        Task task = new Task();
        when(modelMapper.map(taskObject, Task.class)).thenReturn(task);
        when(taskMapper.insertSelective(task)).thenReturn(1);
        int result = taskRepository.createTask(taskObject);
        assertEquals(1, result);
        verify(taskMapper, times(1)).insertSelective(task);
    }

    @Test
    public void testUpdateTask() {
        TaskObject taskObject = new TaskObject();
        Task task = new Task();
        when(modelMapper.map(taskObject, Task.class)).thenReturn(task);
        when(taskMapper.updateByPrimaryKey(task)).thenReturn(1);
        int result = taskRepository.updateTask(taskObject);
        assertEquals(1, result);
        verify(taskMapper, times(1)).updateByPrimaryKey(task);
    }

    @Test
    public void testDeleteTask() {
        when(taskMapper.deleteByPrimaryKey(anyInt())).thenReturn(1);
        int result = taskRepository.deleteTask(1);
        assertEquals(1, result);
        verify(taskMapper, times(1)).deleteByPrimaryKey(anyInt());
    }
}

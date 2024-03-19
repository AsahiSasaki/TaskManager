package com.excite.taskmanager.unitTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.excite.taskmanager.application.controller.TaskController;
import com.excite.taskmanager.application.resource.gen.org.openapitools.model.TaskPostBody;
import com.excite.taskmanager.application.resource.gen.org.openapitools.model.TaskResponseBody;
import com.excite.taskmanager.domain.exception.TaskNotExistException;
import com.excite.taskmanager.domain.exception.ValidationException;
import com.excite.taskmanager.domain.object.TaskObject;
import com.excite.taskmanager.domain.service.TaskService;
import com.excite.taskmanager.domain.service.TaskValidation;

@SpringBootTest
public class TaskControllerTest {

    @InjectMocks
    private TaskController taskController;

    @Mock
    private TaskService taskService;

    @Mock
    private TaskValidation taskValidation;

    @Mock
    private ModelMapper modelMapper;

    @Test
    public void testGetTasks() throws Exception {
        List<TaskObject> taskObjects = Arrays.asList(new TaskObject(), new TaskObject());
        List<TaskResponseBody> taskResponseBodies = Arrays.asList(new TaskResponseBody(), new TaskResponseBody());

        when(taskService.getTasks()).thenReturn(taskObjects);
        when(modelMapper.map(taskObjects, new TypeToken<List<TaskResponseBody>>() {
        }.getType())).thenReturn(taskResponseBodies);

        ResponseEntity<List<TaskResponseBody>> response = taskController.getTasks();

        assertEquals(ResponseEntity.ok().body(taskResponseBodies), response);
    }

    @Test
    public void testGetTaskByID_Success() throws TaskNotExistException {
        TaskObject task = new TaskObject();
        when(taskService.getTaskById(anyInt())).thenReturn(task);

        TaskObject ret = taskService.getTaskById(anyInt());

        TaskResponseBody taskResponseBody = new TaskResponseBody();
        when(modelMapper.map(ret, TaskResponseBody.class)).thenReturn(taskResponseBody);

        ResponseEntity<TaskResponseBody> response = taskController.getTaskByID(1);

        assertEquals(ResponseEntity.ok().body(taskResponseBody), response);
    }

    @Test
    public void testGetTaskByID_TaskNotExistException() throws TaskNotExistException {
        when(taskService.getTaskById(anyInt())).thenThrow(new TaskNotExistException(anyInt()));

        assertThrows(TaskNotExistException.class, () -> {
            taskController.getTaskByID(1);
        });
    }

    @Test
    public void testUpdateTask_Success() throws TaskNotExistException {
        TaskObject task = new TaskObject();
        doNothing().when(taskValidation).validate(task);;
    }

    @Test
    public void testDeleteTask_Success() throws TaskNotExistException {
        int taskId = 1;
        doNothing().when(taskService).deleteTask(taskId);

        assertDoesNotThrow(() -> {
            taskController.deleteTask(taskId);
        });
    }

    @Test
    public void testDeleteTask_TaskNotExistException() throws TaskNotExistException {
        int id = 1;
        doThrow(new TaskNotExistException(id)).when(taskService).deleteTask(id);

        assertThrows(TaskNotExistException.class, () -> {
            taskController.deleteTask(id);
        });
    }

}

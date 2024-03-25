package com.excite.taskmanager.unitTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.excite.taskmanager.application.controller.TaskController;
import com.excite.taskmanager.application.resource.gen.org.openapitools.model.TaskPostBody;
import com.excite.taskmanager.application.resource.gen.org.openapitools.model.TaskPutBody;
import com.excite.taskmanager.application.resource.gen.org.openapitools.model.TaskResponseBody;
import com.excite.taskmanager.domain.exception.TaskNotExistException;
import com.excite.taskmanager.domain.exception.ValidationException;
import com.excite.taskmanager.domain.object.TaskObject;
import com.excite.taskmanager.domain.service.TaskService;
import com.excite.taskmanager.common.TaskValidation;

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

    private TaskObject task;
    private TaskPostBody taskPostBody;
    private TaskPutBody taskPutBody;
    private TaskResponseBody taskResponseBody;
    private TaskNotExistException taskNotExistException;
    private ValidationException validationException;
    private int id;
    private String errorMessage;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        task = new TaskObject();
        taskPostBody = new TaskPostBody();
        taskPutBody = new TaskPutBody();
        taskResponseBody = new TaskResponseBody();
        id = new Random().nextInt(Integer.MAX_VALUE) + 1;
        taskNotExistException = new TaskNotExistException(id);
        errorMessage = "タスク名は必須です";
        validationException = new ValidationException(errorMessage);
    }

    @Test
    public void testGetTasks() throws Exception {
        List<TaskObject> taskObjects = Arrays.asList(task, task);
        List<TaskResponseBody> taskResponseBodies = Arrays.asList(taskResponseBody, taskResponseBody);

        when(taskService.getTasks()).thenReturn(taskObjects);
        when(modelMapper.map(taskObjects, new TypeToken<List<TaskResponseBody>>() {
        }.getType())).thenReturn(taskResponseBodies);

        assertEquals(ResponseEntity.ok().body(taskResponseBodies), taskController.getTasks());
    }

    @Test
    public void testGetTaskByID_Success() throws TaskNotExistException {
        when(taskService.getTaskById(anyInt())).thenReturn(task);
        when(modelMapper.map(task, TaskResponseBody.class)).thenReturn(taskResponseBody);

        assertEquals(ResponseEntity.ok().body(taskResponseBody), taskController.getTaskByID(id));
    }

    @Test
    public void testGetTaskByID_TaskNotExistException() throws TaskNotExistException {
        when(taskService.getTaskById(anyInt())).thenThrow(taskNotExistException);

        assertThrows(TaskNotExistException.class, () -> taskController.getTaskByID(id));
    }

    @Test
    public void testCreateTask_Success() throws ValidationException {
        try (MockedStatic<TaskValidation> mocked = Mockito.mockStatic(TaskValidation.class)) {
            mocked.when(() -> TaskValidation.validate(task)).thenAnswer(invocation -> null);
            doNothing().when(taskService).createTask(task);

            assertDoesNotThrow(() -> taskController.createTask(taskPostBody));
        }
    }

    @Test
    public void testCreateTask_ValidationException() {
        try (MockedStatic<TaskValidation> mocked = Mockito.mockStatic(TaskValidation.class)) {
            mocked.when(() -> {
                TaskValidation.validate(any(TaskObject.class));
            }).thenThrow(validationException);

            when(modelMapper.map(any(TaskPostBody.class), eq(TaskObject.class))).thenReturn(task);
            Exception exception = assertThrows(ValidationException.class, () -> {
                taskController.createTask(taskPostBody);
            });

            assertEquals(errorMessage, exception.getMessage());
        }
    }

    @Test
    public void testUpdateTask_Success() throws ValidationException, TaskNotExistException {
        try (MockedStatic<TaskValidation> mocked = Mockito.mockStatic(TaskValidation.class)) {
            when(modelMapper.map(any(TaskPutBody.class), eq(TaskObject.class))).thenReturn(task);
            mocked.when(() -> {
                TaskValidation.validate(task);
            }).thenAnswer(invocation -> null);
            doNothing().when(taskService).updateTask(task);

            assertDoesNotThrow(() -> {
                taskController.updateTask(id, taskPutBody);
            });
        }
    }

    @Test
    public void testUpdateTask_ValidationException() throws ValidationException, TaskNotExistException {
        try (MockedStatic<TaskValidation> mocked = Mockito.mockStatic(TaskValidation.class)) {
            TaskObject task = Mockito.mock(TaskObject.class);
            when(modelMapper.map(any(TaskPutBody.class), eq(TaskObject.class))).thenReturn(task);
            mocked.when(() -> {
                TaskValidation.validate(task);
            }).thenThrow(validationException);
            doNothing().when(taskService).updateTask(task);
            Exception exception = assertThrows(ValidationException.class, () -> {
                taskController.updateTask(id, taskPutBody);
            });

            assertEquals(errorMessage, exception.getMessage());
        }
    }

    @Test
    public void testUpdateTask_TaskNotExistException() throws ValidationException, TaskNotExistException {
        try (MockedStatic<TaskValidation> mocked = Mockito.mockStatic(TaskValidation.class)) {
            when(modelMapper.map(any(TaskPutBody.class), eq(TaskObject.class))).thenReturn(task);
            mocked.when(() -> {
                TaskValidation.validate(task);
            }).thenAnswer(invocation -> null);
            doThrow(taskNotExistException).when(taskService).updateTask(task);
            assertThrows(TaskNotExistException.class, () -> {
                taskController.updateTask(id, taskPutBody);
            });
        }
    }

    @Test
    public void testDeleteTask_Success() throws TaskNotExistException {
        doNothing().when(taskService).deleteTask(id);

        assertDoesNotThrow(() -> {
            taskController.deleteTask(id);
        });
    }

    @Test
    public void testDeleteTask_TaskNotExistException() throws TaskNotExistException {
        doThrow(taskNotExistException).when(taskService).deleteTask(id);

        assertThrows(TaskNotExistException.class, () -> {
            taskController.deleteTask(id);
        });
    }

}

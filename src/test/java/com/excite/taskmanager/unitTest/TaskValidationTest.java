package com.excite.taskmanager.unitTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.excite.taskmanager.domain.exception.ValidationException;
import com.excite.taskmanager.domain.object.TaskObject;
import com.excite.taskmanager.domain.service.TaskValidation;

@SpringBootTest
public class TaskValidationTest {

    @Test
    public void testNo1() {
        TaskObject task = new TaskObject();
        task.setDeadline(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));

        Exception exception = assertThrows(ValidationException.class, () -> {
            TaskValidation.validate(task);
        });

        String expectedMessage = "タスク名は必須です";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testNo2() {
        TaskObject task = new TaskObject();
        task.setTitle("");
        task.setDeadline(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));

        Exception exception = assertThrows(ValidationException.class, () -> {
            TaskValidation.validate(task);
        });

        String expectedMessage = "タスク名は必須です";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testNo3() {
        TaskObject task = new TaskObject();
        task.setTitle("telecaster");
        task.setDeadline(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));

        assertDoesNotThrow(() -> TaskValidation.validate(task));
    }

}

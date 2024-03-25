package com.excite.taskmanager.unitTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.excite.taskmanager.domain.exception.ValidationException;
import com.excite.taskmanager.domain.object.TaskObject;
import com.excite.taskmanager.common.TaskValidation;

@SpringBootTest
public class TaskValidationTest {

    // 指定した文字数の文字列を作る
    private String generateString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append("あ");
        }
        return sb.toString();
    }

    @Test
    public void titleNull_ValidationException() {
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
    public void titleBlank_ValidationException() {
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
    public void descriptionNull_Success() {
        TaskObject task = new TaskObject();
        task.setTitle("telecaster");
        task.setDeadline(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));

        assertDoesNotThrow(() -> TaskValidation.validate(task));
    }

    @Test
    public void descriptionBlank_Success() {
        TaskObject task = new TaskObject();
        task.setTitle("telecaster");
        task.setDescription("");
        task.setDeadline(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));

        assertDoesNotThrow(() -> TaskValidation.validate(task));
    }

    @Test
    public void descriptionNonHalfWidth_Success() {
        TaskObject task = new TaskObject();
        task.setTitle("telecaster");
        task.setDescription("ストライプ");
        task.setDeadline(Date.from(LocalDate.now().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()));

        assertDoesNotThrow(() -> TaskValidation.validate(task));
    }

    @Test
    public void descriptionHasHalfWidth_ValidationException() {
        TaskObject task = new TaskObject();
        task.setTitle("telecaster");
        task.setDescription("stripe");
        task.setDeadline(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));

        Exception exception = assertThrows(ValidationException.class, () -> {
            TaskValidation.validate(task);
        });

        String expectedMessage = "タスク内容に半角入力は許可されていません";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void descriptionExceeding_ValidationException() {
        TaskObject task = new TaskObject();
        task.setTitle("telecaster");
        task.setDescription(generateString(51));
        task.setDeadline(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));

        Exception exception = assertThrows(ValidationException.class, () -> {
            TaskValidation.validate(task);
        });

        String expectedMessage = "タスク内容は50文字以下で入力してください";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void deadlinePastDate_ValidationException() {
        TaskObject task = new TaskObject();
        task.setTitle(generateString(20));
        task.setDescription("ストライプ");
        task.setDeadline(Date.from(LocalDate.now().minusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()));

        Exception exception = assertThrows(ValidationException.class, () -> {
            TaskValidation.validate(task);
        });

        String expectedMessage = "過去の日付は許可されていません";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void borderLength_Success() {
        TaskObject task = new TaskObject();
        task.setTitle(generateString(20));
        task.setDescription(generateString(50));
        task.setDeadline(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));

        assertDoesNotThrow(() -> TaskValidation.validate(task));
    }

    @Test
    public void titleExceeding_ValidationException() {
        TaskObject task = new TaskObject();
        task.setTitle(generateString(21));
        task.setDeadline(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));

        Exception exception = assertThrows(ValidationException.class, () -> {
            TaskValidation.validate(task);
        });

        String expectedMessage = "タスク名は20文字以下で入力してください";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}

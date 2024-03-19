package com.excite.taskmanager.domain.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.regex.Pattern;

import com.excite.taskmanager.domain.exception.ValidationException;
import com.excite.taskmanager.domain.object.TaskObject;

public class TaskValidation {

    public static Object validate;

    public static void validate(TaskObject data) throws ValidationException {
        validateTitle(data.getTitle());
        validateDescription(data.getDescription());
        validateDeadline(data.getDeadline());
    }

    private static void validateTitle(String title) throws ValidationException {
        if (title == null || title.strip().isEmpty()) {
            throw new ValidationException("タスク名は必須です");
        }

        if (title.length() > 20) {
            throw new ValidationException("タスク名は20文字以下で入力してください");
        }
    }

    private static void validateDescription(String description) throws ValidationException {
        if (description != null && description.length() > 50) {
            throw new ValidationException("タスク内容は50文字以下で入力してください");
        }

        if (description != null && Pattern.compile("[\\x20-\\x7E]").matcher(description).find()) {
            throw new ValidationException("タスク内容に半角入力は許可されていません");
        }
    }

    private static void validateDeadline(Date deadline) throws ValidationException {
        if (deadline != null) {
            LocalDate deadlineDate = deadline.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate today = LocalDate.now();

            if (deadlineDate.isBefore(today)) {
                throw new ValidationException("過去の日付は許可されていません");
            }
        }
    }
}

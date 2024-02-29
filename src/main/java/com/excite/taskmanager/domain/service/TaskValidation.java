package com.excite.taskmanager.domain.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.regex.Pattern;

import com.excite.taskmanager.domain.exception.ValidationException;
import com.excite.taskmanager.domain.object.TaskObject;

public class TaskValidation {

    public static void validate(TaskObject data) throws ValidationException {
        if (data.title == null || data.title.strip().isEmpty()) {
            throw new ValidationException("タスク名は必須です");
        }

        if (data.title.length() > 10) {
            throw new ValidationException("タスク名は10文字以下で入力してください");
        }

        if (data.description != null && data.description.length() > 50) {
            throw new ValidationException("タスク内容は50文字以下で入力してください");
        }

        if (Pattern.compile("[\\x20-\\x7E]").matcher(data.description).find()) {
            throw new ValidationException("タスク内容に半角入力は許可されていません");
        }

        if (data.deadline != null) {
            LocalDate deadlineDate = data.deadline.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate today = LocalDate.now();

            if (deadlineDate.isBefore(today)) {
                throw new ValidationException("過去の日付は許可されていません");
            }
        }
    }
}
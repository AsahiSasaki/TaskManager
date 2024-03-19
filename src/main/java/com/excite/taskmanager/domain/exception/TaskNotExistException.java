package com.excite.taskmanager.domain.exception;

public class TaskNotExistException extends Exception {

    public TaskNotExistException(int id) {
        super("ID：" + id + "のタスクは存在しません。");
    }

}
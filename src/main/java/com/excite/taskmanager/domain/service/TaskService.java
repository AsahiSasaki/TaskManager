package com.excite.taskmanager.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.excite.taskmanager.domain.object.TaskObject;
import com.excite.taskmanager.domain.repository.TaskRepository;
import com.excite.taskmanager.domain.exception.ValidationException;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    /**
     * タスク一覧取得
     *
     */
    public List<TaskObject> getTasks() throws Exception {
        return taskRepository.getTasks();
    }

    /**
     * タスク取得
     *
     * @param id
     */
    public TaskObject getTaskById(int id) {
        return taskRepository.getTaskById(id);
    }

    /**
     * タスク作成
     *
     * @param TaskObject
     */
    public void createTask(TaskObject data) throws ValidationException {
        TaskValidation.validate(data);
        taskRepository.createTask(data);
    }

    /**
     * タスク更新
     *
     * @param TaskObject
     */
    public int updateTask(TaskObject data) throws ValidationException {
        TaskValidation.validate(data);
        return taskRepository.updateTask(data);
    }

    /**
     * タスク削除
     *
     * @param id
     */
    public int deleteTask(int id) {
        int deleteRecord = taskRepository.deleteTask(id);
        return deleteRecord;
    }
}

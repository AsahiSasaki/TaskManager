package com.excite.taskmanager.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.excite.taskmanager.domain.object.TaskObject;
import com.excite.taskmanager.domain.repository.TaskRepository;
import com.excite.taskmanager.domain.exception.TaskNotExistException;

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
    public TaskObject getTaskById(int id) throws TaskNotExistException {
        TaskObject ret = taskRepository.getTaskById(id);
        if (ret == null) {
            throw new TaskNotExistException(id);
        }
        return ret;
    public TaskObject getTaskById(int id) throws TaskNotExistException {
        TaskObject ret = taskRepository.getTaskById(id);
        if (ret == null) {
            throw new TaskNotExistException(id);
        }
        return ret;
    }

    /**
     * タスク作成
     *
     * @param TaskObject
     */
    public void createTask(TaskObject data) {
    public void createTask(TaskObject data) {
        taskRepository.createTask(data);
    }

    /**
     * タスク更新
     *
     * @param TaskObject
     */
    public void updateTask(TaskObject data) throws TaskNotExistException {
        int ret = taskRepository.updateTask(data);
        if (ret == 0) {
            throw new TaskNotExistException(data.getId());
        }
    public void updateTask(TaskObject data) throws TaskNotExistException {
        int ret = taskRepository.updateTask(data);
        if (ret == 0) {
            throw new TaskNotExistException(data.getId());
        }
    }

    /**
     * タスク削除
     *
     * @param id
     */
    public void deleteTask(int id) throws TaskNotExistException {
        int ret = taskRepository.deleteTask(id);
        if (ret == 0) {
            throw new TaskNotExistException(id);
        }
    public void deleteTask(int id) throws TaskNotExistException {
        int ret = taskRepository.deleteTask(id);
        if (ret == 0) {
            throw new TaskNotExistException(id);
        }
    }
}
package com.excite.taskmanager.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.excite.taskmanager.domain.object.TaskObject;
import com.excite.taskmanager.domain.repository.TaskRepository;

@Service
public class TaskService {
    
    @Autowired
    private TaskRepository taskRepository;

    /**
     * タスク一覧取得
     *
     */
    public List<TaskObject> getTasksList(){
        return taskRepository.getTasks();
    }

    /**
     * タスク取得
     *
     * @param id 
     */
    public TaskObject getTaskById(int id){
        return taskRepository.getTaskById(id);
    }

    /**
     * タスク作成
     *
     * @param TaskObject 
     */
    public void createTask(TaskObject data){
        taskRepository.createTask(data);
    }
    
    /**
     * タスク更新
     *
     * @param TaskObject 
     */
    public void updateTask(TaskObject data){
        taskRepository.updateTask(data);
    }

    /**
     * タスク削除
     *
     * @param id
     */
    public void deleteTask(int id){
        taskRepository.deleteTask(id);
    }
}

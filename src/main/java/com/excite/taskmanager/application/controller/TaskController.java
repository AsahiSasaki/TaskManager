package com.excite.taskmanager.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.modelmapper.ModelMapper;

import com.excite.taskmanager.application.resource.TaskPostBody;
import com.excite.taskmanager.application.resource.TaskPutBody;
import com.excite.taskmanager.domain.object.TaskObject;
import com.excite.taskmanager.domain.service.TaskService;


@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * タスク一覧取得
     *
     * 
     */
    @GetMapping("tasks")
    public ResponseEntity<List<TaskObject>> getTasks() {
        List<TaskObject> ret = taskService.getTasks();
        return ResponseEntity.ok(ret);
    }

    /**
     * タスク取得
     *
     * @param id 
     */
    @GetMapping("tasks/{id}")
    public ResponseEntity<TaskObject> getTaskById(@PathVariable("id") Integer id) {
        TaskObject ret = taskService.getTaskById(id);
        return ResponseEntity.ok(ret);
    }

    /**
     * タスク作成
     *
     * @param 
     */
    @PostMapping("tasks")
    public ResponseEntity<Void> createTask(@RequestBody TaskPostBody taskPostBody) {
        TaskObject reqTaskObject = modelMapper.map(taskPostBody, TaskObject.class);
        taskService.createTask(reqTaskObject);
        return ResponseEntity.ok().build();
    }

    /**
     * タスク更新
     *
     * @param 
     */
    @PutMapping("tasks/{id}")
    public ResponseEntity<Void> updateTask(@PathVariable("id") Integer id, @RequestBody TaskPutBody taskPutBody) {
        TaskObject reqTaskObject = modelMapper.map(taskPutBody, TaskObject.class);
        reqTaskObject.setId(id);
        taskService.updateTask(reqTaskObject);
        return ResponseEntity.ok().build();
    }

    /**
     * タスク削除
     *
     * @param id 
     */
    @DeleteMapping("tasks/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") Integer id) {
            taskService.deleteTask(id);
            return ResponseEntity.ok().build();
    }

}

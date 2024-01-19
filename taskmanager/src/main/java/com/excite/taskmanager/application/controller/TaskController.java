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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.modelmapper.ModelMapper;

import com.excite.taskmanager.application.resource.TaskBody;
import com.excite.taskmanager.domain.object.TaskObject;
import com.excite.taskmanager.domain.service.TaskService;


@RestController
@RequestMapping("")
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
    @GetMapping("taskmanager")
    public ResponseEntity<List<TaskObject>> getTasksList() {
        List<TaskObject> ret = taskService.getTasksList();
        return ResponseEntity.ok(ret);
    }

    /**
     * タスク取得
     *
     * @param id 
     */
    @GetMapping("taskmanager/{id}")
    public ResponseEntity<TaskObject> getTaskById(@PathVariable("id") Integer id) {
        TaskObject ret = taskService.getTaskById(id);
        return ResponseEntity.ok(ret);
    }

    /**
     * タスク作成
     *
     * @param 
     */
    @PostMapping("taskmanager")
    public void createTask(@RequestBody TaskBody taskBody) {
        TaskObject reqTaskObject = modelMapper.map(taskBody, TaskObject.class);
        taskService.createTask(reqTaskObject);
    }

    /**
     * タスク更新
     *
     * @param 
     */
    @PutMapping("taskmanager")
    public void updateTask(@RequestBody TaskBody taskBody) {
        TaskObject reqTaskObject = modelMapper.map(taskBody, TaskObject.class);
        taskService.updateTask(reqTaskObject);
    }

    /**
     * タスク削除
     *
     * @param id 
     */
    @DeleteMapping("taskmanager/{id}")
    public void deleteTask(@PathVariable("id") Integer id) {
            taskService.deleteTask(id);
    }

}

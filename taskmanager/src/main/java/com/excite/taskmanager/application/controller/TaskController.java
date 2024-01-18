package com.excite.taskmanager.application.controller;

import org.modelmapper.internal.bytebuddy.asm.Advice.OffsetMapping.Target.ForArray.ReadWrite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.excite.taskmanager.domain.object.TaskObject;
import com.excite.taskmanager.domain.service.TaskService;


@RestController
@RequestMapping("")
public class TaskController {

    @Autowired
    private TaskService taskService;


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
     * タスク削除
     *
     * @param id 
     */
    @DeleteMapping("taskmanager/{id}")
    public void deleteTask(@PathVariable("id") Integer id) {
            taskService.deleteTask(id);
    }
    
}

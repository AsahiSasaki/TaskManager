package com.excite.taskmanager.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

// import com.excite.taskmanager.application.resource.TaskPostBody;
import org.openapitools.model.TaskPostBody;
import com.excite.taskmanager.application.resource.TaskPutBody;
import com.excite.taskmanager.application.resource.TaskResponseBody;
import com.excite.taskmanager.domain.exception.TaskNotExistException;
import com.excite.taskmanager.domain.exception.ValidationException;
import com.excite.taskmanager.domain.object.TaskObject;
import com.excite.taskmanager.domain.service.TaskService;

@RestController
// @CrossOrigin(origins = "http://localhost:5173")
@CrossOrigin(origins = "*")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * タスク一覧取得
     * 
     * @throws Exception
     */
    @GetMapping("tasks")
    public ResponseEntity<List<TaskResponseBody>> getTasks() throws Exception {
        List<TaskResponseBody> res = modelMapper.map(taskService.getTasks(), new TypeToken<List<TaskResponseBody>>() {
        }.getType());

        return ResponseEntity.ok().body(res);
    }

    /**
     * タスク取得
     *
     * @param id
     * @throws TaskNotExistException
     */
    @GetMapping("tasks/{id}")
    public ResponseEntity<TaskResponseBody> getTaskById(@PathVariable("id") int id) throws TaskNotExistException {
        TaskObject ret = taskService.getTaskById(id);
        if (ret == null) {
            throw new TaskNotExistException(id);
        }
        TaskResponseBody res = modelMapper.map(ret, TaskResponseBody.class);
        return ResponseEntity.ok().body(res);
    }

    /**
     * タスク作成
     *
     * @param
     * @throws ValidationException
     */
    @PostMapping("tasks")
    public ResponseEntity<String> createTask(@RequestBody TaskPostBody taskPostBody) throws ValidationException {
        TaskObject reqTaskObject = modelMapper.map(taskPostBody, TaskObject.class);
        taskService.createTask(reqTaskObject);
        return ResponseEntity.ok().build();
    }

    /**
     * タスク更新
     *
     * @param
     * @throws ValidationException,TaskNotExistException
     */
    @PutMapping("tasks/{id}")
    public ResponseEntity<Void> updateTask(@PathVariable("id") int id, @RequestBody TaskPutBody taskPutBody)
            throws ValidationException, TaskNotExistException {
        TaskObject reqTaskObject = modelMapper.map(taskPutBody, TaskObject.class);
        reqTaskObject.setId(id);
        int ret = taskService.updateTask(reqTaskObject);
        if (ret == 0) {
            throw new TaskNotExistException(id);
        }
        return ResponseEntity.ok().build();
    }

    /**
     * タスク削除
     *
     * @param id
     * @throws TaskNotExistException
     */
    @DeleteMapping("tasks/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable("id") int id) throws TaskNotExistException {
        int ret = taskService.deleteTask(id);
        if (ret == 0) {
            throw new TaskNotExistException(id);
        }
        return ResponseEntity.ok().build();
    }

}

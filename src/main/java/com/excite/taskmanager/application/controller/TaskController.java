package com.excite.taskmanager.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import com.excite.taskmanager.application.resource.gen.org.openapitools.model.TaskPostBody;
import com.excite.taskmanager.application.resource.gen.org.openapitools.model.TaskPutBody;
import com.excite.taskmanager.application.resource.gen.org.openapitools.model.TaskResponseBody;
import com.excite.taskmanager.common.TaskValidation;
import com.excite.taskmanager.application.resource.gen.org.openapitools.api.TasksApi;
import com.excite.taskmanager.domain.exception.TaskNotExistException;
import com.excite.taskmanager.domain.exception.ValidationException;
import com.excite.taskmanager.domain.object.TaskObject;
import com.excite.taskmanager.domain.service.TaskService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class TaskController implements TasksApi {

    @Autowired
    private TaskService taskService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * タスク一覧取得
     * 
     * @throws Exception
     */
    @Override
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
    @Override
    public ResponseEntity<TaskResponseBody> getTaskByID(Integer id)
            throws TaskNotExistException {
        TaskObject ret = taskService.getTaskById(id);
        TaskResponseBody res = modelMapper.map(ret, TaskResponseBody.class);
        return ResponseEntity.ok().body(res);
    }

    /**
     * タスク作成
     *
     * @param
     * @throws ValidationException
     */
    @Override
    public ResponseEntity<Void> createTask(@Valid TaskPostBody taskPostBody) throws ValidationException {
        TaskObject reqTaskObject = modelMapper.map(taskPostBody, TaskObject.class);
        TaskValidation.validate(reqTaskObject);
        TaskValidation.validate(reqTaskObject);
        taskService.createTask(reqTaskObject);
        return ResponseEntity.ok().build();
    }

    /**
     * タスク更新
     *
     * @param
     * @throws ValidationException,TaskNotExistException
     */
    @Override
    public ResponseEntity<Void> updateTask(Integer id, @Valid TaskPutBody taskPutBody)
            throws ValidationException, TaskNotExistException {
        TaskObject reqTaskObject = modelMapper.map(taskPutBody, TaskObject.class);
        reqTaskObject.setId(id);
        TaskValidation.validate(reqTaskObject);
        taskService.updateTask(reqTaskObject);
        TaskValidation.validate(reqTaskObject);
        taskService.updateTask(reqTaskObject);
        return ResponseEntity.ok().build();
    }

    /**
     * タスク削除
     *
     * @param id
     * @throws TaskNotExistException
     */
    @Override
    public ResponseEntity<Void> deleteTask(Integer id) throws  TaskNotExistException {
        taskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }

}
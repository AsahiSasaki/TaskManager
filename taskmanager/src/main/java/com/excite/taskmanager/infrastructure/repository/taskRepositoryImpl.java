package com.excite.taskmanager.infrastructure.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import com.excite.taskmanager.domain.repository.TaskRepository;
import com.excite.taskmanager.domain.object.TaskObject;
import com.excite.taskmanager.infrastructure.entity.Task;
import com.excite.taskmanager.infrastructure.mapper.TaskMapper;

@Repository
public class TaskRepositoryImpl implements TaskRepository{

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<TaskObject> getTasksList() {

        List<Task> ret = taskMapper.selectByExample(null);
        List<TaskObject> tasksList = new ArrayList<TaskObject>();
        ret.forEach(i -> tasksList.add(modelMapper.map(i, TaskObject.class)));
        return tasksList;
    }

    @Override
    public TaskObject getTaskById(Integer id) {

        TaskObject ret = modelMapper.map(taskMapper.selectByPrimaryKey(id), TaskObject.class);

        return ret;
    }

    @Override
    public int createTask(TaskObject data) {

        var entity = modelMapper.map(data, Task.class);
        int ret = taskMapper.insert(entity);

        return ret;
    }

    @Override
    public int updateTask(TaskObject data) {

        var entity = modelMapper.map(data, Task.class);
        int ret = taskMapper.updateByPrimaryKey(entity);

        return ret;
    }

    @Override
    public void deleteTask(Integer id) {

        taskMapper.deleteByPrimaryKey(id);
    }
}

package com.excite.taskmanager.infrastructure.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import com.excite.taskmanager.domain.repository.TaskRepository;
import com.excite.taskmanager.domain.object.TaskObject;
import com.excite.taskmanager.infrastructure.entity.Task;
import com.excite.taskmanager.infrastructure.mapper.TaskMapper;


@Repository
public class TaskRepositoryImpl implements TaskRepository {

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<TaskObject> getTasks() {

        List<Task> ret = taskMapper.selectByExample(null);

        return modelMapper.map(ret, new TypeToken<List<TaskObject>>() {
        }.getType());
    }

    @Override
    public TaskObject getTaskById(int id) {
        Task task = taskMapper.selectByPrimaryKey(id);
        if (task == null) {
            return null;
        }
        TaskObject ret = modelMapper.map(task, TaskObject.class);
        return ret;
    }

    @Override
    public int createTask(TaskObject data) {

        Task entity = modelMapper.map(data, Task.class);
        int ret = taskMapper.insertSelective(entity);

        return ret;
    }

    @Override
    public int updateTask(TaskObject data) {

        Task entity = modelMapper.map(data, Task.class);
        int ret = taskMapper.updateByPrimaryKey(entity);

        return ret;
    }

    @Override
    public int deleteTask(int id) {
        int ret = taskMapper.deleteByPrimaryKey(id);
        return ret;
    }
}

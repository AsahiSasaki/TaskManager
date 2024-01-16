package com.excite.taskmanager.infrastructure.repository;

import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.modelmapper.ModelMapper;
import com.excite.taskmanager.domain.repository.TaskRepository;
import com.excite.taskmanager.domain.object.TaskObject;
import com.excite.taskmanager.infrastructure.entity.Task;
import com.excite.taskmanager.infrastructure.mapper.TaskMapper;


public class taskRepositoryImpl implements TaskRepository{

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private ModelMapper modelMapper;



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
    public int deleteTask(int id) {

        int ret = taskMapper.deleteByPrimaryKey(id);

        return ret;
    }
}

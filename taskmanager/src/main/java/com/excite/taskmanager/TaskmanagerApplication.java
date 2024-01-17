package com.excite.taskmanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import com.excite.taskmanager.domain.service.TaskService;

@SpringBootApplication
public class TaskmanagerApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(TaskmanagerApplication.class, args);
	}

}

package com.excite.taskmanager.application.resource;

import java.util.Date;

import lombok.Data;

@Data
public class TaskPostBody {

    /**
     * タスク名
     */
    private String title;

    /**
     * タスク内容
     */
    private String description;

    /**
     * 期限
     */
    private Date deadline;

}

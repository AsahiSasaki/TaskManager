package com.excite.taskmanager.application.resource;

import java.util.Date;

import lombok.Data;

@Data
public class TaskPutBody {

    /**
     *　ID
     */
    private Integer id;

    /**
     * タスク名
     */
    private String title;

    /**
     * タスク内容
     */
    private String description;

    /**
     * ステータス
     * 未完了：0、完了：1
     */
    private Integer status;

    /**
     * 期限
     */
    private Date deadline;
    
}

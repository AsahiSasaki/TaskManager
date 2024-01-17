package com.excite.taskmanager.domain.object;

import java.util.Date;

public class  TaskObject {
    
    /**
     * タスクID
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

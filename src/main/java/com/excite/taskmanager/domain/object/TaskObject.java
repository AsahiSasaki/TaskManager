package com.excite.taskmanager.domain.object;

import java.util.Date;
import lombok.Data;

@Data
public class  TaskObject {
    
    /**
     * タスクID
     */
    private int id;

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
    private int status;

    /**
     * 期限
     */
    private Date deadline;
}

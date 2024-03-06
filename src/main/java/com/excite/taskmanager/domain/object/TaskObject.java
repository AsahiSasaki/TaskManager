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
    public String title;

    /**
     * タスク内容
     */
    public String description;

    /**
     * ステータス
     * 未完了：0、完了：1
     */
    public int status;

    /**
     * 期限
     */
    public Date deadline;
}

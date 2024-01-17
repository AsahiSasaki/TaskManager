package com.excite.taskmanager.domain.repository;

import com.excite.taskmanager.domain.object.TaskObject;

/**
 * インフラ層とのインタフェース
 */

public interface TaskRepository {

    /**
     * タスク一覧取得
     *
     * @return タスク一覧
     */
    public Object getTasksList();

    /**
     *タスク取得
     * 
     * @param id 
     * @return タスク
     */
    public TaskObject getTaskById(int id);

    /**
     * タスク作成
     *
     * @param data Taskオブジェクト
     * @return 更新レコード数
     */
    public int createTask(TaskObject data);

    /**
     * タスク更新
     *
     * @param data Taskオブジェクト
     * @return 更新レコード数
     */
    public int updateTask(TaskObject data);

    /**
     * タスク削除
     *
     * @param id 
     * @return 更新レコード数
     */
    public void deleteTask(int id);
}

package com.example.caculator;

/**
 * 命令接口
 *
 * @author ligl
 */
public interface Command<T extends Number> {
    /**
     * 执行操作
     */
    void execute();

    /**
     * 撤销操作
     */
    void unexecute();

}

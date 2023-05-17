package com.example.caculator;

import java.util.ArrayList;
import java.util.List;

/**
 * 通知命令执行者
 *
 * @author ligl
 */
public class Invoker<T extends Number> {
    /**
     * 计算器
     */
    private Calculator<T> calculator;
    /**
     * 用于记录历史操作
     */
    private List<Command<T>> historyCommands = new ArrayList<>();
    /**
     * 当前执行步数
     */
    private int current = 0;

    /**
     * 用于确定泛型类型
     *
     * @param type
     */
    public Invoker(Class<T> type) {
        this.calculator = new Calculator<>(type);
    }

    public void redo(int levels) {
        for (int i = 0; i < levels; i++) {
            if (current < historyCommands.size()) {
                historyCommands.get(current).execute();
                current++;
            }
        }
    }

    public void undo(int levels) {
        for (int i = 0; i < levels; i++) {
            if (current > 0) {
                --current;
                historyCommands.get(current).unexecute();
            }
        }
    }

    public void compute(char operator, T operand) {
        Command command = new CalculatorCommand(calculator, operator, operand);
        command.execute();
        historyCommands.add(command);
        current++;
    }

    /**
     * 获取计算结果
     */
    public T getResult() {
        return calculator.getResult();
    }

}
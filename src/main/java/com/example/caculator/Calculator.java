package com.example.caculator;

import lombok.extern.slf4j.Slf4j;

/**
 * 可以实现两个数的加、减、乘、除运算，并可以进行undo和redo操作的计算器类
 * 实现泛型版本，稍微影响性能
 *
 * @param <T>
 * @author ligl
 */
@Slf4j
public class Calculator<T extends Number> {
    private T result;

    public Calculator(Class<T> type) {
        if (Integer.class.equals(type)) {
            result = (T) Integer.valueOf(0);
        } else if (Double.class.equals(type)) {
            result = (T) Double.valueOf(0d);
        } else {
            throw new IllegalArgumentException("Unsupported number type!");
        }
    }

    public void operation(char operator, T operand) {
        result = calculate(getResult(), operand, operator);
    }

    private T calculate(T num1, T num2, char operator) {
        // 根据运算符进行计算
        if (num1 instanceof Integer) {
            // 防止 Integer 越界，使用long保存结果，会损失一定的性能
            // Java 17 Lambda 写法
            long result = switch (operator) {
                case '+' -> (long) num1.intValue() + num2.intValue();
                case '-' -> (long) num1.intValue() - num2.intValue();
                case '*' -> (long) num1.intValue() * num2.intValue();
                case '/' -> (long) num1.intValue() / num2.intValue();
                default -> throw new IllegalArgumentException("Unsupported operate type!");
            };
            if (result > Integer.MAX_VALUE) {
                result = Integer.MAX_VALUE;
                log.warn("计算结果超过了 Integer 最大值");
            } else if (result < Integer.MIN_VALUE) {
                result = Integer.MIN_VALUE;
                log.warn("计算结果超过了 Integer 最小值");
            }
            return (T) Integer.valueOf((int) result);
        } else if (num1 instanceof Double) {
            return switch (operator) {
                case '+' -> (T) Double.valueOf(num1.doubleValue() + num2.doubleValue());
                case '-' -> (T) Double.valueOf(num1.doubleValue() - num2.doubleValue());
                case '*' -> (T) Double.valueOf(num1.doubleValue() * num2.doubleValue());
                case '/' -> (T) Double.valueOf(num1.doubleValue() / num2.doubleValue());
                default -> throw new IllegalArgumentException("Unsupported operate type!");
            };
        } else {
            throw new IllegalArgumentException("Unsupported number type!");
        }
    }

    /**
     * 获取计算结果
     */
    public T getResult() {
        return result;
    }

}

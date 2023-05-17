package com.example.caculator;

/**
 * 计算指令实现类
 *
 * @author ligl
 */
public class CalculatorCommand<T extends Number> implements Command<T> {

    private Calculator<T> calculator;
    private char operator;
    private T operand;

    public CalculatorCommand(Calculator<T> calculator, char operator, T operand) {
        super();
        this.calculator = calculator;
        this.operator = operator;
        this.operand = operand;
    }

    @Override
    public void execute() {
        calculator.operation(operator, operand);

    }

    @Override
    public void unexecute() {
        calculator.operation(undo(operator), operand);
    }

    /**
     * @param operator2
     * @return
     */
    private char undo(char operator2) {
        // Java 17 Lambda 写法
        char ch = switch (operator2) {
            case '+' -> '-';
            case '-' -> '+';
            case '*' -> '/';
            case '/' -> '*';
            default -> 0;
        };
        return ch;
    }
}

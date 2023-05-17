package com.example.caculator;

import org.junit.jupiter.api.*;

@Nested
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Integer类型测试")
class IntegerCalculatorTest {

    private Invoker<Integer> invoker;

    @BeforeEach
    @DisplayName("初始化命令执行者")
    void before() {
        invoker = new Invoker<>(Integer.class);
    }

    @AfterEach
    @DisplayName("回收命令执行者")
    void clear() {
        invoker = null;
    }


    @Test
    @Order(1)
    @DisplayName("加法")
    void add() {
        invoker.compute('+', 100);
        Assertions.assertEquals(100, invoker.getResult());
        invoker.compute('+', Integer.MAX_VALUE);
        // 计算结果越界直接返回 Integer.MAX_VALUE
        Assertions.assertEquals(Integer.MAX_VALUE, invoker.getResult());
    }

    @Test
    @Order(2)
    @DisplayName("减法")
    void subtract() {
        invoker.compute('+', 100);
        invoker.compute('-', 99);
        Assertions.assertEquals(1, invoker.getResult());
    }

    @Test
    @Order(3)
    @DisplayName("乘法")
    void multiply() {
        invoker.compute('+', 2);
        invoker.compute('*', 3);
        Assertions.assertEquals(6, invoker.getResult());
    }

    @Test
    @Order(4)
    @DisplayName("除法")
    void divide() {
        invoker.compute('+', 6);
        invoker.compute('/', 2);
        Assertions.assertEquals(3, invoker.getResult());
    }

    @Test
    @Order(5)
    @DisplayName("撤销")
    void undo() {
        invoker.compute('+', 100);
        invoker.compute('-', 50);
        invoker.compute('*', 20);
        invoker.compute('/', 2);
        // 撤销一步
        invoker.undo(1);
        // 结果应为 1000
        // 即 （100 - 50） * 20
        Assertions.assertEquals(1000, invoker.getResult());
        // 再撤销两步
        invoker.undo(2);
        Assertions.assertEquals(100, invoker.getResult());
        // 再撤销五步（最多四步。越界测试）
        invoker.undo(5);
        Assertions.assertEquals(0, invoker.getResult());
    }

    @Test
    @Order(6)
    @DisplayName("重做")
    void redo() {
        invoker.compute('+', 100);
        invoker.compute('-', 50);
        invoker.compute('*', 20);
        invoker.compute('/', 2);
        // 撤销两步
        invoker.undo(2);
        // 重做一步
        invoker.redo(1);
        Assertions.assertEquals(1000, invoker.getResult());
        // 重做五步 (最多四步，越界测试)
        invoker.redo(5);
        Assertions.assertEquals(500, invoker.getResult());
    }

}
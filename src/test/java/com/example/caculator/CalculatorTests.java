package com.example.caculator;

import org.junit.jupiter.api.*;


@Nested
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Double类型测试")
class DoubleCalculatorTest {

    private Invoker<Double> invoker;

    @BeforeEach
    @DisplayName("初始化命令执行者")
    void before() {
        invoker = new Invoker<>(Double.class);
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
        invoker.compute('+', 100d);
        Assertions.assertEquals(100d, invoker.getResult());
    }

    @Test
    @Order(2)
    @DisplayName("减法")
    void subtract() {
        invoker.compute('+', 100d);
        invoker.compute('-', 99d);
        Assertions.assertEquals(1d, invoker.getResult());
    }

    @Test
    @Order(3)
    @DisplayName("乘法")
    void multiply() {
        invoker.compute('+', 2d);
        invoker.compute('*', 3d);
        Assertions.assertEquals(6d, invoker.getResult());
    }

    @Test
    @Order(4)
    @DisplayName("除法")
    void divide() {
        invoker.compute('+', 6d);
        invoker.compute('/', 2d);
        Assertions.assertEquals(3d, invoker.getResult());
    }

    @Test
    @Order(5)
    @DisplayName("撤销")
    void undo() {
        invoker.compute('+', 100d);
        invoker.compute('-', 50d);
        invoker.compute('*', 20d);
        invoker.compute('/', 2d);
        // 撤销一步
        invoker.undo(1);
        // 结果应为 1000
        // 即 （100 - 50） * 20
        Assertions.assertEquals(1000d, invoker.getResult());
        // 再撤销两步
        invoker.undo(2);
        Assertions.assertEquals(100d, invoker.getResult());
        // 再撤销五步（最多四步。越界测试）
        invoker.undo(5);
        Assertions.assertEquals(0d, invoker.getResult());
    }

    @Test
    @Order(6)
    @DisplayName("重做")
    void redo() {
        invoker.compute('+', 100d);
        invoker.compute('-', 50d);
        invoker.compute('*', 20d);
        invoker.compute('/', 2d);
        // 撤销两步
        invoker.undo(2);
        // 重做一步
        invoker.redo(1);
        Assertions.assertEquals(1000d, invoker.getResult());
        // 重做五步 (最多四步，越界测试)
        invoker.redo(5);
        Assertions.assertEquals(500d, invoker.getResult());
    }

}
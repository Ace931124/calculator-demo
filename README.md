# Calculator

## 说明

创建了一个 Command 接口，定义了 execute() 和 unexecute() 两个方法。
在 Invoker 类中，我将历史操作用集合记录下来，以便做撤销和重做操作。
通过使用命令模式，每个操作封装成了一个命令对象，实现了操作的解耦和可扩展性。

## 版本

Java 17

## 单元测试

执行以下命令

```
mvn clean test
```

可知计算器能正常工作。
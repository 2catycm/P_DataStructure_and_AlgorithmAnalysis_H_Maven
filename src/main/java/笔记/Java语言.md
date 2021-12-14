## Inner class
### class access modifier
#### 一般的class
https://www.decodejava.com/java-class-access-modifiers.htm#:~:text=Class%20Access%20Modifiers%20A%20Java%20class%20can%20be,us%20see%20different%20kinds%20of%20class%20access%20modifiers.
首先public和default很清楚。这是对于外部class 而言的，或者对于文件而言的。
注意，不允许private 和 protected的外部类。
#### 内部类的访问限制
https://docs.oracle.com/javase/tutorial/java/javaOO/innerclasses.html
Modifiers
You can use the same modifiers for inner classes that you use for other members of the outer class. For example, you can use the access specifiers private, public, and protected to restrict access to inner classes, just as you use them to restrict access do to other class members.

### inner new and inner this
https://www.journaldev.com/996/java-inner-class#:~:text=Java%20inner%20class%20is%20defined%20inside%20the%20body,Java%20Nested%20classes%20are%20divided%20into%20two%20types.
OuterClass outerObject = new OuterClass();
OuterClass.InnerClass innerObject = outerObject.new InnerClass();

### 经典用法
- iterator是经典的内部类，而不是内部静态类。
If a class is useful to only one class, it makes sense to keep it nested and together. It helps in the packaging of the classes.
那么iterator是public内部类还是private内部类？
还是要对外面暴露的吧
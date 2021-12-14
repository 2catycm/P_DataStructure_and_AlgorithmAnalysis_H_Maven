package util.algorithm;

import java.util.function.Predicate;
import java.util.function.Supplier;

//Answer这个抽象数据类型必须是良序的。 然后定义在Answer的某个集合上的某个函数必须是单调的
public abstract class BinaryAnswerSearcher<Answer extends Comparable<Answer>> {
    private Predicate<Answer> answerIsValid;
    private Supplier<Answer> answerDomain;
}

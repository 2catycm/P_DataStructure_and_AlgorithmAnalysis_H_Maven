package lab4;

import org.junit.jupiter.api.Test;
import util.judge.Judge;

import java.util.Arrays;
import java.util.Random;
//版本1检查出来的问题：
//expected:a,
//        actually:IndexOutOfBoundsException,
//        at String:ea
//        at Operations: Operation{type=FIND, operand1=2, operand2=2} Operation{type=TRANSFORM, operand1=2, operand2=1}
//        at operation: Operation{type=FIND, operand1=2, operand2=2}
//ea
//1
//2 2

//二轮错误
//expected:c,
//        actually:e,
//        at String:ceeeb
//        at Operations: Operation{type=INSERT, operand1=2, operand2=2} Operation{type=INSERT, operand1=2, operand2=2} Operation{type=FIND, operand1=3, operand2=1} Operation{type=FIND, operand1=4, operand2=3} Operation{type=FIND, operand1=2, operand2=2}
//        at operation: Operation{type=FIND, operand1=3, operand2=1}

//ceeeb
//3
//1 c 2
//1 c 2
//2 3
//没错！！是对拍的暴力错了
class Problem6Test {
    private static boolean correctCasesHint = false;
    private static int option = 0;
    private static int[] stringSizes = {5, 2, 10000, 8, 10};
    private static int[] operationSizes = {5, 2, 10000, 8, 10};
    private static int[] ranges = {5, 5, 5, 5, 5};
    private static int stringSize = stringSizes[option];// <= 2000000
    private static int operationSize = operationSizes[option];// <= 100000
    private static int range = ranges[option]; //26以内
    private static int casePassed = 0;
//    @Test
    public static void main(String[] args) {
        step1:
        while (true){
            final String string = Judge.nextRandomAlphabetString(stringSize, range);
            final SolvableList bruteForceSolutionList   = new BruteForceSolutionList(string);
            final SolvableList solutionList             = new SolutionList(string);
            final Random random = Judge.random;
            final Operation[] operations = nextRandomOperations(operationSize, stringSize);
            String solve1 = "", solve2 = "";
            for (int i = 0; i < operationSize; i++) {
                try {
                    solve1 = operations[i].operate(bruteForceSolutionList);
                    solve2 = operations[i].operate(solutionList);
                }catch (NullPointerException e){
                    System.err.printf("expected:%s, \nactually:null pointer exception, \nat String:%s\nat Operations: %s\nat operation: %s\n\n", solve1, string, Judge.ArraysToString(operations), operations[i]);
                    continue step1;
                }catch (IndexOutOfBoundsException e){
                    System.err.printf("expected:%s, \nactually:IndexOutOfBoundsException, \nat String:%s\nat Operations: %s\nat operation: %s\n\n", solve1, string, Judge.ArraysToString(operations), operations[i]);
                    e.printStackTrace();
                    continue step1;
                }
                if (!solve1.equals(solve2)) {
                    System.err.printf("expected:%s, \nactually:%s, \nat String:%s\nat Operations: %s\nat operation: %s\n\n", solve1, solve2,string,  Judge.ArraysToString(operations), operations[i]);
                }else if (correctCasesHint){
                    System.out.print(++casePassed+" operations has passed.\r");
                }
            }
            if (correctCasesHint) {
                System.out.println("new String to test.");
            }

//            assertEquals(nodes1, nodes2);
        }
    }

    private static Operation[] nextRandomOperations(int size, int stringSize){
        final Operation[] operations = new Operation[size];
        for (int i = 0; i < size; i++) {
            operations[i] = new Operation(OperationType.values()[Judge.random.nextInt(3)], Judge.random.nextInt(stringSize)+1, Judge.random.nextInt(stringSize)+1);//1, stringSize).charAt(0));
        }
        return operations;
    }
    private enum OperationType {INSERT, FIND, TRANSFORM}
    private static class Operation{
        private OperationType type;
        private int operand1, operand2;
        public String operate(SolvableList list){
            switch (type){
                case INSERT:
                    list.insert((char) ((operand1%26)+'a'), operand2);
                    return "";
                case FIND:
                    return list.find(operand1)+"";
                case TRANSFORM:
                    list.transform(Math.min(operand1, operand2), Math.max(operand1, operand2));
                    return "";
                default:
                    return null;
            }
        }
        public Operation(OperationType type, int operand1, int operand2) {
            this.type = type;
            this.operand1 = operand1;
            this.operand2 = operand2;
        }

        @Override
        public String toString() {
            return "Operation{" +
                    "type=" + type +
                    ", operand1=" + operand1 +
                    ", operand2=" + operand2 +
                    '}';
        }
    }
}
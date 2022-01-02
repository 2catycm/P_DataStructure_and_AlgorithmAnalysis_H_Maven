package lab5_stack_queue;

import java.util.InputMismatchException;
import java.util.Objects;

public class Problem1 {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();

    public static void main(String[] args) {
        final int T = in.nextInt();
//        int[] hungryStudentCount = {0};
        //食物栈和学生队
        final LinkedStack<String> foods = new LinkedStack<>();
        final LinkedQueue<String> studentPreferences = new LinkedQueue<>();
        //canteen open
        for (int i = 0; i < T; i++) {
            final String operation = in.next();
            switch (operation){
                case "NewFood":
                    foods.入栈(in.next());
                    break;
                case "NewComer":
                    studentPreferences.排队(in.next());
//                    hungryStudentCount[0]++;
                    break;
                case "TakeFood":
                    try {
                        takeFood(foods, studentPreferences/*, hungryStudentCount[0]*/);//java的指针传递
                    }catch (IndexOutOfBoundsException e){
                        break;
                    }
                    break;
                default:
                    throw new InputMismatchException("输入不对。");
            }
        }
        //canteen off
        outer:
        while (!foods.栈空()){
            boolean nobodyCouldGetHisFavouriteFood = true;
            for (int i = 0; i < studentPreferences.队长(); i++) {
                try {
                    if (takeFood(foods, studentPreferences))
                        nobodyCouldGetHisFavouriteFood = false;
                }catch(IndexOutOfBoundsException e){
                    break outer;
                }
            }
            if (nobodyCouldGetHisFavouriteFood)
                break;
        }
        //final decision
        System.out.println(studentPreferences.队长()==0?"Qi Fei!":studentPreferences.队长());
    }

    private static boolean takeFood(LinkedStack<String> foods, LinkedQueue<String> studentPreferences/*, int[] hungryStudentCount*/) throws IndexOutOfBoundsException{
        if (foods.栈空())
            throw new IndexOutOfBoundsException("no food remain.");
        final String food = foods.出栈();
        if (!Objects.equals(studentPreferences.队首(), food)) {
            foods.入栈(food);//放回去
            studentPreferences.排队(studentPreferences.带检查的办理业务());
            return false;
        }
        else {
            studentPreferences.带检查的办理业务();
            return true;
//            hungryStudentCount[0]--;
        }
    }
}
//头文件。 还没有实现递归include，所以要按两次BakeAll
//#include "OJReader.java"
//#include "OJWriter.java"
//#include "StackAndQueue打包.java"
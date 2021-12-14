package lab2;

public class LectureTest {
    public static void main(String[] args) {
        printS(0, 100);
    }
    public static void printS(int low, int high){
        assert low<=high;
        System.out.println(low);
        if (low==high) ;
        else{
            printS(low+1,high);
        }
    }
}

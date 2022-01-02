package lab5_stack_queue;

class MaximumDequeTest {
    //1 3 2 4 5
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();
    private static int testingSize = 3*1000_000;
    public static void main(String[] args) {
//        final var ints = in.nextIntegerArray(5);
        final Integer[] ints = new Integer[testingSize];
        for (int i = 0; i < testingSize; i++) {
//            ints[i] =1;
//            ints[i] =i;
            ints[i] =testingSize-i;
        }
//        MaximumDeque<Integer> maximumDeque = new MaximumDeque<>(ints, 5, Integer::compare);
//        MaximumDeque<Integer> maximumDeque = new MaximumDeque<>(ints, 3, Integer::compare);
//        MaximumDeque<Integer> maximumDeque = new MaximumDeque<>(ints, testingSize, Integer::compare);
        MaximumDeque<Integer> maximumDeque = new MaximumDeque<>(ints, testingSize/2, Integer::compare);
//        while (maximumDeque.valid()){
//            System.out.println(maximumDeque.getMax());
//            maximumDeque.slide();
//        }
        while (maximumDeque.canSlide()){
            maximumDeque.slide();
            out.print(maximumDeque.getMax()+" ");
        }

    }
}
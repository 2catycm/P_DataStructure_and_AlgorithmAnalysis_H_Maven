package lab4_linkedlist;

//#include "Term.java"
//#include "OJReader.java"
//#include "链表打包.java"
public class Problem1New {
    private static OJReader in = new OJReader();
    private static LinkedNodes<Term> readTerms(int length){
        final LinkedNodes<Term> terms = new LinkedNodes<Term>();
        for (int i = 0; i < length; i++) {
            terms.add(new Term(in.nextInt(), in.nextInt()));
        }
        return terms;
    }
    public static void main(String[] args) {
        int n = in.nextInt();
        int m = in.nextInt();
        final LinkedNodes<Term> list1 = readTerms(n);
        final LinkedNodes<Term> list2 = readTerms(m);
        System.out.println(solveArray(list1, list2));
    }
    //correct
    static StringBuilder solveArray(LinkedNodes<Term> list1, LinkedNodes<Term> list2){
        final Term[] array1 = new Term[list1.size()];
        final Term[] array2 = new Term[list2.size()];
        int i = 0;
        for(var e:list1) {
            array1[i++] = e;
        }
        i = 0;
        for(var e:list2) {
            array2[i++] = e;
        }
        int q = 0;
        final StringBuilder result = new StringBuilder();
        for (int j = 0, k = 0;j<array1.length||k< array2.length; q++) {//有一个还有就可以输出
            if (j>= array1.length)
                result.append(array2[k++]).append("\n");
            else if (k>= array2.length)
                result.append(array1[j++]).append("\n");
            else {
                int comparison = array1[j].compareTo(array2[k]);
                if (comparison==0){
                    result.append(array1[j++].add(array2[k++])).append("\n");
                }else if (comparison<0){
                    result.append(array2[k++]).append("\n");
                }else
                    result.append(array1[j++]).append("\n");
            }
        }
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(q).append("\n").append(result);
        return stringBuilder;
    }
    static StringBuilder solve(LinkedNodes<Term> list1, LinkedNodes<Term> list2) {
        final StringBuilder result = new StringBuilder();
        int k = 0;
        final Pointer<Term> pointer1 = list1.pointer(), pointer2 = list2.pointer();
//        pointer1.前进(); pointer2.前进();
        for (; pointer1.还未指向虚无() &&pointer2.还未指向虚无();){
            int comparison = pointer1.所指之物().compareTo(pointer2.所指之物());
            if (comparison==0) {
                result.append(pointer1.所指之物().add(pointer2.所指之物()));
                pointer1.前进();
                pointer2.前进();
            }
            else if (comparison<0){//term1小，term2大。消耗term2
                result.append(pointer2.所指之物());
                pointer2.前进();
            }else{
                result.append(pointer1.所指之物());
                pointer1.前进();
            }
            result.append("\n");
            k++;
        }
        while (pointer1.还未指向虚无()){
            result.append(pointer1.所指之物()); //忘记换行了！
            pointer1.前进();
            k++;
        }
        while (pointer2.hasNext()){
            result.append(pointer2.所指之物());//忘记换行了！
            pointer2.前进();
            k++;
        }
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(k).append("\n").append(result);
        return stringBuilder;
    }
}

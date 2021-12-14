package util.data_structure;

import java.util.Arrays;
import java.util.stream.IntStream;

class IntPrefixSumTable {
    private long[] prefixSum;
    public IntPrefixSumTable(int[] source){
        prefixSum = new long[source.length+1];
        IntStream.range(0, source.length).parallel().forEach(i->prefixSum[i+1] = source[i]);
        Arrays.parallelPrefix(prefixSum, Long::sum);
    }
    public long sumAmong(int startInclusive, int endExclusive){
        return prefixSum[endExclusive]-prefixSum[startInclusive];
    }
}
//Sparse Table, not Symbol Table. Range Maximum/Minimum Query Problem.
class IntDoublingSTTable {
    private int[][] st;//存储下标，而非最大值。这样信息才是完整的.
    private int[] source;//source从0开始，记得减1
    public IntDoublingSTTable(int[] source) {
        this.source = source;
        int jMax = (int)(Math.log(source.length)/Math.log(2));//我们不需要让每一个st[i]覆盖数组，而是要保证查询时需要且仅需要两个st[i][j]来定位。比如，长度为7，那么j只需要做到2, 2**2的长度足够了。
        st = new int[source.length][jMax+1];  //st的意义。 st[i][j] 表示source[i]开始，长度为2**j的区间中最大值在source中的下标。 jMax本身也要取到，所以+1
        for (int i = 0; i < source.length; i++) {//j=0的情况，每一个st[i]都可以很轻松的得到，因为区间长度为2**0=1, 有且只有一个元素。
            st[i][0] = i;
        }
        for (int j = 1; j <= jMax; j++) {//j=1-jMax的情况
            for (int i = 0; (i+(1<<j) )<= source.length; i++) {//比如，j=2， i=3， 数组是0 1 ... 6， 长度为7. 那么，i现在可以进去，下一次不可以进去。
                int leftHalfMaxIndex = st[i][j-1];
                int rightHalfMaxIndex = st[i+(1<<(j-1))][j-1];//比如，现在是i=0， j=2，   右边的一半，从2开始到3.从0跃迁到2，需要加上2的j-1次方。
                if (source[leftHalfMaxIndex]>source[rightHalfMaxIndex])
                    st[i][j] = leftHalfMaxIndex;
                else
                    st[i][j] = rightHalfMaxIndex;
            }
        }
    }
    public int indexOfMaxAmong(int startInclusive, int endExclusive){//查询source[startInclusive-endExclusive] 中最大值的index。
        //区间长度在2的k次方和k+1次方之间（可取2的k次方，不可以取k+1次方），比如，2^2<=4<2^3;
        int k = (int)(Math.log(endExclusive-startInclusive)/Math.log(2));
        int leftHalfMaxIndex = st[startInclusive][k];
        int rightHalfMaxIndex = st[endExclusive-(1<<k)][k];//比如，长度为5。 0 1 2 3 4    excluded:5. 应该为5-2^2=1开始的.
        if (source[leftHalfMaxIndex]>source[rightHalfMaxIndex])
            return leftHalfMaxIndex;
        else
            return rightHalfMaxIndex;
    }

    public int[] getSource() {
        return source;
    }
}

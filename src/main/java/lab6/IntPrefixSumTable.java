package lab6;

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


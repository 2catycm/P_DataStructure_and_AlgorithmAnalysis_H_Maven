package AlgorithmAnalysis;

import org.apache.commons.lang3.time.StopWatch;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
//import org.springframework.util.StopWatch;

public class AlgorithmAnalyst {
    private final Method algorithm;
    private Object[] data;


    public long predictTimeOfScale(int n){//ms
        if (growthRateOfPolynomial==null)
            growthRateOfPolynomial = growthRateOfPolynomial();
        if (constantA==null)
            constantA = 0;
        return (long)(constantA*Math.pow(n,growthRateOfPolynomial));
    }
    private Integer constantA;

    // for example the T(n)~n^2, we will return 2;
    // implemented by doing doubling test
    public double growthRateOfPolynomial(){
        if (growthRateOfPolynomial!=null)
            return growthRateOfPolynomial;
        return -1;
    }
    private Double growthRateOfPolynomial;

    public long timeTrail() throws InvocationTargetException, IllegalAccessException { //return the execution time under given data
        if (data==null)
            throw new ArrayIndexOutOfBoundsException("You haven't set data to the algorithm yet.");
        StopWatch stopWatch = new StopWatch();
        algorithm.invoke(null, data);
        stopWatch.stop();
        return stopWatch.getTime(TimeUnit.MILLISECONDS);
    }
    public AlgorithmAnalyst(Method algorithm) throws InvocationTargetException, IllegalAccessException {
        this.algorithm = algorithm;
//        System.out.println(algorithm.getParameterTypes()[0].equals(int[].class));
//        algorithm.invoke(null,new int[]{1},2,3d);

    }
    public Method getAlgorithm() {
        return algorithm;
    }
    public Object[] getData() {
        return data;
    }

    public void setData(Object[] data) {
        this.data = data;
    }

    private static void test(int[] a, int b,double c){
        System.out.println("hello");
    }
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final AlgorithmAnalyst getAlgorithm = new AlgorithmAnalyst(AlgorithmAnalyst.class.getDeclaredMethod("test", int[].class, int.class, double.class));
    }
}

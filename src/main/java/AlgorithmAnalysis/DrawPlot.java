package AlgorithmAnalysis;

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class DrawPlot {
    private static Scanner in =  new Scanner(System.in);
    public static void main(String[] args) {
        ArrayList<Point> arrayList = new ArrayList<>();
        String[] split = new String[0];
        while (in.hasNext()){
            split = in.nextLine().split(",");
            arrayList.add(new Point(Integer.parseInt(split[0]), Integer.parseInt(split[1])));
        }
        StdDraw.setXscale(0, Integer.parseInt(split[0]));
        StdDraw.setYscale(0, Integer.parseInt(split[1]));
        StdDraw.setPenColor(StdDraw.RED);
//        StdDraw.enableDoubleBuffering();
        StdDraw.setPenRadius(0.005);
        for(Point e:arrayList){
            StdDraw.point(e.x,e.y);
        }
        StdDraw.show();
    }
}

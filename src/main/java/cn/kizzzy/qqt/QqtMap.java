package cn.kizzzy.qqt;

import java.util.List;

public class QqtMap {
    public int version;
    public int reserved01;
    public int reserved02;
    public int width;
    public int height;
    
    public List<int[][]> layers;
    
    public int pointCount0;
    public Point[] points0;
    
    public int pointCount1;
    public Point[] points1;
    
    public int pointCount2;
    public Point[] points2;
    
    public int pointCount3;
    public Point[] points3;
    
    public static class Point {
        public int x;
        public int y;
    }
}

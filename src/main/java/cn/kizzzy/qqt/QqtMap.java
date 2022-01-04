package cn.kizzzy.qqt;

import java.util.List;

public class QqtMap {
    public int version;
    public int reserved01;
    public int reserved02;
    public int width;
    public int height;
    
    public List<int[][]> layers;
    
    public int unknownCount;
    public Unknown[] unknowns;
    
    public Points[] points;
    
    public static class Unknown {
        public int reserved01;
        public int reserved02;
        public int reserved03;
        public int reserved04;
    }
    
    public static class Points {
        public int count;
        public Point[] points;
    }
    
    public static class Point {
        public int x;
        public int y;
    }
}

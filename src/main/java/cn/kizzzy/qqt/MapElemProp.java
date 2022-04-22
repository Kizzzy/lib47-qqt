package cn.kizzzy.qqt;

public class MapElemProp {
    
    public int version;
    
    public Element[] elements;
    
    public static class Element {
        public int id;
        public int width;
        public int height;
        public int x;
        public int y;
        public int life;
        public int level;
        public int special;
        public int[] attrs;
    }
}

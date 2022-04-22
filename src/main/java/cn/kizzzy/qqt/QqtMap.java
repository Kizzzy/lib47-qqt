package cn.kizzzy.qqt;

public class QqtMap {
    
    /**
     * 版本
     */
    public int version;
    
    /**
     * 游戏模式
     *
     * @see GameMode
     */
    public int gameMode;
    
    /**
     * 最大人数
     */
    public int maxPlayer;
    
    /**
     * 宽
     */
    public int width;
    
    /**
     * 高
     */
    public int height;
    
    /**
     * <p>1、顶层</p>
     * <p>2、网格</p>
     * <p>3、地板</p>
     */
    public Layer[] layers;
    
    /**
     * 道具类型及其掉落概率
     */
    public Drop[] drops;
    
    /**
     * <p>1、可以被炸毁的元素的点，也就是允许的道具生成点。</p>
     * <p>2、在竞技的标准模式下，第1个队伍的玩家出生点。</p>
     * <p>3、在竞技的标准模式下，第2个队伍的玩家出生点。</p>
     * <p>4、特殊模式下，标识属于双方队伍各自的特殊地图元素，例如包子铺、雕塑塔、机械大炮、糖客战基地等。</p>
     */
    public Points[] points;
    
    public static class Layer {
        public Element[][] elements;
    }
    
    public static class Element {
        
        public int value;
        
        public Element() {
            this(0);
        }
        
        public Element(int value) {
            this.value = value;
        }
        
        public int city() {
            return value / 1000;
        }
        
        public int id() {
            return value % 1000;
        }
    }
    
    // 道具类型及其掉落概率
    public static class Drop {
        public int id;
        public int min;
        public int max;
        public float rate;
    }
    
    public static class Points {
        public Point[] points;
    }
    
    public static class Point {
        public int x;
        public int y;
    }
}

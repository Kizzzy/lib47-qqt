package cn.kizzzy.vfs.handler;

import java.util.HashMap;
import java.util.Map;

public abstract class QqtImageFileHandler<T> extends StreamFileHandler<T> {
    
    protected static final Map<Integer, IReadParam> readParamKvs
        = new HashMap();
    
    protected boolean checkValid(int width, int height) {
        return width > 0 && width < 4096 && height > 0 && height < 4096;
    }
    
    static {
        readParamKvs.put(3, new ARGBReadParam(2));
        readParamKvs.put(8, new ARGBReadParam(4));
        readParamKvs.put(285212672, new ARGBReadParam(2));
    }
    
    public static class DxtReadParam implements IReadParam {
        private int unit;
        
        public DxtReadParam(int unit) {
            this.unit = unit;
        }
        
        public int Calc(int width, int height) {
            return this.find2n(width) * this.find2n(height) * this.unit;
        }
        
        private int find2n(int target) {
            int temp = target - 1;
            int[] var3 = new int[]{
                1,
                2,
                4,
                8,
                16
            };
            int var4 = var3.length;
            
            for (int var5 = 0; var5 < var4; ++var5) {
                int shift = var3[var5];
                temp |= temp >> shift;
            }
            
            return temp < 0 ? 1 : temp + 1;
        }
    }
    
    public static class ARGBReadParam implements IReadParam {
        private int unit;
        
        public ARGBReadParam(int unit) {
            this.unit = unit;
        }
        
        public int Calc(int width, int height) {
            return width * height * this.unit;
        }
    }
    
    public interface IReadParam {
        int Calc(int var1, int var2);
    }
}

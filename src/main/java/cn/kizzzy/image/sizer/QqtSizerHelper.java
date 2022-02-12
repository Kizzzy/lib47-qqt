package cn.kizzzy.image.sizer;

import cn.kizzzy.image.Sizer;

import java.util.HashMap;
import java.util.Map;

public class QqtSizerHelper {
    
    protected static final Map<Integer, Sizer> sizerKvs;
    
    static {
        sizerKvs = new HashMap<>();
        sizerKvs.put(0, new ARGBSizer(2));
        sizerKvs.put(1, new ARGBSizer(4));
    }
    
    public static int calc(int type, int width, int height) {
        Sizer sizer = sizerKvs.get(type);
        if (sizer != null) {
            return sizer.calc(width, height);
        }
        return 0;
    }
}

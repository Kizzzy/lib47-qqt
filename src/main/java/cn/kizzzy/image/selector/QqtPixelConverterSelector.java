package cn.kizzzy.image.selector;

import cn.kizzzy.image.PixelConverter;
import cn.kizzzy.image.converter.ARGB0565PixelConverter;
import cn.kizzzy.image.converter.ARGB8888PixelConverter;

import java.util.HashMap;
import java.util.Map;

public class QqtPixelConverterSelector extends DefaultPixelConverterSelector {
    
    public QqtPixelConverterSelector() {
        super(initKvs());
    }
    
    private static Map<Integer, PixelConverter> initKvs() {
        Map<Integer, PixelConverter> converterKvs = new HashMap<>();
        converterKvs.put(0x03, new ARGB0565PixelConverter());
        converterKvs.put(0x08, new ARGB8888PixelConverter());
        converterKvs.put(285212672, new ARGB0565PixelConverter());
        return converterKvs;
    }
}

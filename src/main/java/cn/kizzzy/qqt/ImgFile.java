package cn.kizzzy.qqt;

import cn.kizzzy.vfs.IInputStreamGetter;
import cn.kizzzy.vfs.stream.HolderInputStreamGetter;

public class ImgFile extends HolderInputStreamGetter {
    
    public int magic01;
    
    public int magic02;
    
    public short major;
    
    public short minor;
    
    public int headerSize;
    
    public int count;
    
    public int planes;
    
    public int offsetX;
    
    public int offsetY;
    
    public int maxWidth;
    
    public int maxHeight;
    
    public Frame[] frames;
    
    public static class Frame {
        
        public int reserved01;
        
        public int offsetX;
        
        public int offsetY;
        
        public int reserved04;
        
        public int width;
        
        public int height;
        
        public int reserved07;
        
        // -------------------- extra field --------------------
        
        public int index;
        
        public boolean valid;
        
        public ImgFile file;
        
        public IInputStreamGetter pixels;
        
        public IInputStreamGetter alphas;
    }
}

package cn.kizzzy.qqt;

import cn.kizzzy.io.IFullyReader;
import cn.kizzzy.io.SliceFullReader;
import cn.kizzzy.vfs.IInputStreamGetter;

public class ImgFile implements IInputStreamGetter {
    
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
    
    // -------------------- extra field --------------------
    
    private IInputStreamGetter source;
    
    public IInputStreamGetter getSource() {
        return source;
    }
    
    public void setSource(IInputStreamGetter source) {
        this.source = source;
    }
    
    public static class Frame implements IInputStreamGetter {
        
        public int reserved01;
        
        public int offsetX;
        
        public int offsetY;
        
        public int reserved04;
        
        public int width;
        
        public int height;
        
        public int reserved07;
        
        // -------------------- extra field --------------------
        
        public int index;
        
        public long offset;
        
        public long size;
        
        public long offset_alpha;
        
        public long size_alpha;
        
        public boolean valid;
        
        public ImgFile file;
        
        public IInputStreamGetter getSource() {
            return file;
        }
        
        public void setSource(IInputStreamGetter source) {
            this.file = (ImgFile) source;
        }
        
        public IFullyReader getInput() throws Exception {
            if (this.getSource() == null) {
                throw new NullPointerException("source is null");
            }
            return new SliceFullReader(getSource().getInput(), offset, size);
        }
        
        public IFullyReader OpenStream_Alpha() throws Exception {
            if (this.getSource() == null) {
                throw new NullPointerException("source is null");
            }
            return new SliceFullReader(getSource().getInput(), offset_alpha, size_alpha);
        }
    }
}

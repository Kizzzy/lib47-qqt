package cn.kizzzy.qqt;

import cn.kizzzy.io.IFullyReader;
import cn.kizzzy.io.SliceFullReader;
import cn.kizzzy.vfs.IStreamable;

public class QqtImgItem implements IStreamable {
    public int reserved01;
    public int offsetX;
    public int offsetY;
    public int reserved04;
    public int width;
    public int height;
    public int reserved07;
    
    public int index;
    public boolean valid;
    public QqtImg file;
    
    public long offset;
    public int size;
    
    public long offset_alpha;
    public int size_alpha;
    
    public IStreamable getSource() {
        return file;
    }
    
    public void setSource(IStreamable source) {
        this.file = (QqtImg) source;
    }
    
    public IFullyReader OpenStream() throws Exception {
        if (this.getSource() == null) {
            throw new NullPointerException("source is null");
        }
        return new SliceFullReader(getSource().OpenStream(), offset, size);
    }
    
    public IFullyReader OpenStream_Alpha() throws Exception {
        if (this.getSource() == null) {
            throw new NullPointerException("source is null");
        }
        return new SliceFullReader(getSource().OpenStream(), offset_alpha, size_alpha);
    }
}

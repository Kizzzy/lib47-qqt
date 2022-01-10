package cn.kizzzy.qqt;

import cn.kizzzy.io.FullyReader;
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
    
    public FullyReader OpenStream() throws Exception {
        if (this.getSource() == null) {
            throw new NullPointerException("source is null");
        }
        return new SliceFullReader(getSource().OpenStream(), offset, size);
    }
    
    public FullyReader OpenStream_Alpha() throws Exception {
        if (this.getSource() == null) {
            throw new NullPointerException("source is null");
        }
        return new SliceFullReader(getSource().OpenStream(), offset_alpha, size_alpha);
    }
    
    @Override
    public String toString() {
        return "QqtImgItem{" +
            "reserved01=" + reserved01 +
            ", offsetX=" + offsetX +
            ", offsetY=" + offsetY +
            ", reserved04=" + reserved04 +
            ", width=" + width +
            ", height=" + height +
            ", reserved07=" + reserved07 +
            '}';
    }
}

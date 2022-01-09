package cn.kizzzy.qqt;

import cn.kizzzy.vfs.IStreamable;
import cn.kizzzy.io.FullyReader;
import cn.kizzzy.io.SliceFullReader;

public class QqtImgItem implements IStreamable {
    public int reserved01;
    public int reserved02;
    public int reserved03;
    public int reserved04;
    public int width;
    public int height;
    public int reserved07;
    
    public int index;
    public boolean valid;
    public QqtImg file;
    
    public long offset;
    public int size;
    
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
    
    @Override
    public String toString() {
        return "QqtImgItem{" +
            "reserved01=" + reserved01 +
            ", reserved02=" + reserved02 +
            ", reserved03=" + reserved03 +
            ", reserved04=" + reserved04 +
            ", width=" + width +
            ", height=" + height +
            ", reserved07=" + reserved07 +
            '}';
    }
}

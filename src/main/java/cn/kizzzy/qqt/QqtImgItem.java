package cn.kizzzy.qqt;

import cn.kizzzy.vfs.IStreamable;
import cn.kizzzy.io.FullyReader;
import cn.kizzzy.io.SliceFullReader;

public class QqtImgItem implements IStreamable {
    public int reserved01;
    public int reserved02;
    public int reserved03;
    public int type;
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
            "reserved11=" + reserved01 +
            ", reserved12=" + reserved02 +
            ", reserved13=" + reserved03 +
            ", type=" + type +
            ", width=" + width +
            ", height=" + height +
            ", reserved17=" + reserved07 +
            '}';
    }
}

package cn.kizzzy.qqt;

import cn.kizzzy.io.SubStream;
import cn.kizzzy.vfs.IStreamable;

import java.io.InputStream;

public class QqtImg implements IStreamable {
    public int magic01;
    public int magic02;
    public int version;
    public int headerSize;
    public int count;
    public int planes;
    public int reserved7;
    public int reserved8;
    public int maxWidth;
    public int maxHeight;
    public QqtImgItem[] items;
    
    private IStreamable source;
    
    public IStreamable getSource() {
        return source;
    }
    
    public void setSource(IStreamable source) {
        this.source = source;
    }
    
    public InputStream OpenStream() throws Exception {
        if (source == null) {
            throw new NullPointerException("source is null");
        }
        
        InputStream temp = source.OpenStream();
        return new SubStream(temp, 0L, temp.available());
    }
    
    @Override
    public String toString() {
        return "QqtImg{" +
            "magic01=" + magic01 +
            ", magic02=" + magic02 +
            ", version=" + version +
            ", headerSize=" + headerSize +
            ", count=" + count +
            ", planes=" + planes +
            ", reserved7=" + reserved7 +
            ", reserved8=" + reserved8 +
            ", maxWidth=" + maxWidth +
            ", maxHeight=" + maxHeight +
            '}';
    }
}

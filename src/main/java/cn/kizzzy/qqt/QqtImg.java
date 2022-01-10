package cn.kizzzy.qqt;

import cn.kizzzy.vfs.IStreamable;

public class QqtImg implements IStreamable {
    public int magic01;
    public int magic02;
    public int major;
    public int minor;
    public int headerSize;
    public int count;
    public int planes;
    public int offsetX;
    public int offsetY;
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
    
    @Override
    public String toString() {
        return "QqtImg{" +
            "magic01=" + magic01 +
            ", magic02=" + magic02 +
            ", major=" + major +
            ", minor=" + minor +
            ", headerSize=" + headerSize +
            ", count=" + count +
            ", planes=" + planes +
            ", offsetX=" + offsetX +
            ", offsetY=" + offsetY +
            ", maxWidth=" + maxWidth +
            ", maxHeight=" + maxHeight +
            '}';
    }
}

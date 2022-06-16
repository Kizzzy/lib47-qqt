package cn.kizzzy.qqt;

import cn.kizzzy.vfs.IInputStreamGetter;

public class QqtImg implements IInputStreamGetter {
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
    public QqtImgItem[] items;
    
    private IInputStreamGetter source;
    
    public IInputStreamGetter getSource() {
        return source;
    }
    
    public void setSource(IInputStreamGetter source) {
        this.source = source;
    }
}

package cn.kizzzy.qqt;

import cn.kizzzy.io.IFullyReader;
import cn.kizzzy.io.SliceFullReader;
import cn.kizzzy.vfs.IInputStreamGetter;

public class QqtImgItem implements IInputStreamGetter {
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
    
    public IInputStreamGetter getSource() {
        return file;
    }
    
    public void setSource(IInputStreamGetter source) {
        this.file = (QqtImg) source;
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

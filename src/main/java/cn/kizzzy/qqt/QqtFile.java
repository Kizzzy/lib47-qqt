package cn.kizzzy.qqt;

import cn.kizzzy.helper.ZlibHelper;
import cn.kizzzy.io.SubStream;
import cn.kizzzy.vfs.IStreamable;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class QqtFile implements IStreamable {
    public int pathLength;
    public String path;
    public int reserved01;
    public int offset;
    public int originSize;
    public int size;
    
    public String pkg;
    private IStreamable source;
    
    @Override
    public IStreamable getSource() {
        return source;
    }
    
    @Override
    public void setSource(IStreamable source) {
        this.source = source;
    }
    
    @Override
    public InputStream OpenStream() throws Exception {
        if (getSource() == null) {
            throw new NullPointerException("source is null");
        }
        SubStream stream = new SubStream(source.OpenStream(), offset, size);
        byte[] buffer = stream.readBytes(size);
        ByteArrayInputStream bis = new ByteArrayInputStream(ZlibHelper.uncompress(buffer));
        return bis;
    }
    
    @Override
    public String toString() {
        return "QqtFile{" +
            "pkg='" + pkg + '\'' +
            ", pathLength=" + pathLength +
            ", path='" + path + '\'' +
            ", reserved01=" + reserved01 +
            ", offset=" + offset +
            ", originSize=" + originSize +
            ", size=" + size +
            '}';
    }
}

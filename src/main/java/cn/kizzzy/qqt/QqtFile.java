package cn.kizzzy.qqt;

import cn.kizzzy.helper.ZlibHelper;
import cn.kizzzy.vfs.IStreamable;
import cn.kizzzy.io.ByteArrayInputStreamReader;
import cn.kizzzy.io.FullyReader;
import cn.kizzzy.io.SliceFullReader;

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
    public FullyReader OpenStream() throws Exception {
        if (getSource() == null) {
            throw new NullPointerException("source is null");
        }
        FullyReader reader = new SliceFullReader(source.OpenStream(), offset, size);
        byte[] buffer = new byte[size];
        reader.read(buffer);
        return new SliceFullReader(new ByteArrayInputStreamReader(ZlibHelper.uncompress(buffer)));
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

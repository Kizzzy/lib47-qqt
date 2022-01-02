package cn.kizzzy.vfs.handler;

import cn.kizzzy.io.DataOutputStreamEx;
import cn.kizzzy.io.SubStream;
import cn.kizzzy.qqt.QqtFile;
import cn.kizzzy.qqt.QqtIdx;
import cn.kizzzy.vfs.IPackage;

public class QqtIdxFileHandler extends StreamFileHandler<QqtIdx> {
    
    @Override
    protected QqtIdx loadImpl(IPackage vfs, String path, SubStream reader) throws Exception {
        QqtIdx idx = new QqtIdx();
        idx.path = path;
        idx.reserved01 = reader.readIntEx();
        idx.totalCount = reader.readIntEx();
        idx.reserved03 = reader.readIntEx();
        idx.reserved04 = reader.readIntEx();
        for (int i = 0; i < idx.totalCount; ++i) {
            QqtFile file = new QqtFile();
            file.pkg = path;
            file.pathLength = reader.readShortEx();
            file.path = reader.readString(file.pathLength).toLowerCase();
            file.reserved01 = reader.readIntEx();
            file.offset = reader.readIntEx();
            file.originSize = reader.readIntEx();
            file.size = reader.readIntEx();
            
            idx.fileKvs.put(file.path, file);
        }
        return idx;
    }
    
    @Override
    protected void saveImpl(DataOutputStreamEx writer, QqtIdx data) throws Exception {
        throw new UnsupportedOperationException("not support");
    }
}

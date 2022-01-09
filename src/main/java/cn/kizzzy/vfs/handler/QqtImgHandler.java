package cn.kizzzy.vfs.handler;

import cn.kizzzy.helper.LogHelper;
import cn.kizzzy.io.DataOutputStreamEx;
import cn.kizzzy.io.FullyReader;
import cn.kizzzy.io.SeekType;
import cn.kizzzy.qqt.QqtImg;
import cn.kizzzy.qqt.QqtImgItem;
import cn.kizzzy.vfs.IPackage;

public class QqtImgHandler extends QqtImageFileHandler<QqtImg> {
    
    @Override
    protected QqtImg loadImpl(IPackage vfs, String path, FullyReader reader) throws Exception {
        try {
            QqtImg img = new QqtImg();
            img.magic01 = reader.readIntEx();
            img.magic02 = reader.readIntEx();
            img.major = reader.readShortEx();
            img.minor = reader.readShortEx();
            img.headerSize = reader.readIntEx();
            img.count = reader.readIntEx();
            img.planes = reader.readIntEx();
            img.reserved7 = reader.readIntEx();
            img.reserved8 = reader.readIntEx();
            img.maxWidth = reader.readIntEx();
            img.maxHeight = reader.readIntEx();
            
            img.items = new QqtImgItem[img.count];
            for (int i = 0; i < img.items.length; ++i) {
                QqtImgItem item = new QqtImgItem();
                item.index = i;
                item.file = img;
                
                item.reserved01 = reader.readIntEx();
                item.reserved02 = reader.readIntEx();
                item.reserved03 = reader.readIntEx();
                item.reserved04 = reader.readIntEx();
                if (item.reserved04 != 0) {
                    item.width = reader.readIntEx();
                    item.height = reader.readIntEx();
                    item.reserved07 = reader.readIntEx();
                }
                
                item.valid = checkValid(item.width, item.height);
                
                IReadParam param = readParamKvs.get(img.major);
                if (param != null && item.valid) {
                    item.offset = reader.position();
                    item.size = param.Calc(item.width, item.height);
                    
                    reader.seek(item.size, SeekType.CURRENT);
                    
                    if (img.major == 0) {
                        reader.seek((long) item.width * item.height, SeekType.CURRENT);
                    }
                }
                
                img.items[i] = item;
            }
            
            return img;
        } catch (Exception e) {
            LogHelper.error("load qqt img error: " + path, e);
            return null;
        }
    }
    
    @Override
    protected void saveImpl(DataOutputStreamEx writer, QqtImg img) throws Exception {
        throw new UnsupportedOperationException("not support");
    }
}

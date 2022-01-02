package cn.kizzzy.vfs.handler;

import cn.kizzzy.helper.LogHelper;
import cn.kizzzy.io.DataOutputStreamEx;
import cn.kizzzy.io.SubStream;
import cn.kizzzy.qqt.QqtImg;
import cn.kizzzy.qqt.QqtImgItem;
import cn.kizzzy.vfs.IPackage;

public class QqtImgHandler extends QqtImageFileHandler<QqtImg> {
    
    @Override
    protected QqtImg loadImpl(IPackage vfs, String path, SubStream reader) throws Exception {
        try {
            long start = reader.getPosition();
            
            QqtImg img = new QqtImg();
            img.magic01 = reader.readIntEx();
            img.magic02 = reader.readIntEx();
            img.version = reader.readIntEx();
            img.headerSize = reader.readIntEx();
            img.planes = reader.readIntEx();
            img.count = reader.readIntEx();
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
                item.type = reader.readIntEx();
                item.width = reader.readIntEx();
                item.height = reader.readIntEx();
                item.reserved07 = reader.readIntEx();
                
                IReadParam param = readParamKvs.get(item.type);
                if (param != null) {
                    item.offset = (int) (reader.getPosition() - start);
                    item.size = param.Calc(item.width, item.height);
                    reader.skip(param.Calc(item.width, item.height));
                    reader.skip(item.width * item.height);
                    img.items[i] = item;
                }
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

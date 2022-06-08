package cn.kizzzy.qqt.vfs.handler;

import cn.kizzzy.helper.LogHelper;
import cn.kizzzy.qqt.image.sizer.QqtSizerHelper;
import cn.kizzzy.io.IFullyReader;
import cn.kizzzy.io.IFullyWriter;
import cn.kizzzy.io.SeekType;
import cn.kizzzy.qqt.QqtImg;
import cn.kizzzy.qqt.QqtImgItem;
import cn.kizzzy.vfs.IFileHandler;
import cn.kizzzy.vfs.IPackage;

public class QqtImgHandler implements IFileHandler<QqtImg> {
    
    @Override
    public QqtImg load(IPackage vfs, String path, IFullyReader reader, long size) throws Exception {
        try {
            QqtImg img = new QqtImg();
            img.magic01 = reader.readIntEx();
            img.magic02 = reader.readIntEx();
            img.major = reader.readShortEx();
            img.minor = reader.readShortEx();
            img.headerSize = reader.readIntEx();
            img.count = reader.readIntEx();
            img.planes = reader.readIntEx();
            img.offsetX = reader.readIntEx();
            img.offsetY = reader.readIntEx();
            img.maxWidth = reader.readIntEx();
            img.maxHeight = reader.readIntEx();
            
            img.items = new QqtImgItem[img.count];
            for (int i = 0; i < img.items.length; ++i) {
                QqtImgItem item = new QqtImgItem();
                item.index = i;
                item.file = img;
                
                item.reserved01 = reader.readIntEx();
                item.offsetX = reader.readIntEx();
                item.offsetY = reader.readIntEx();
                item.reserved04 = reader.readIntEx();
                if (item.reserved04 != 0) {
                    item.width = reader.readIntEx();
                    item.height = reader.readIntEx();
                    item.reserved07 = reader.readIntEx();
                }
                
                item.valid = 0 < item.width && item.width < 4096
                    && 0 < item.height && item.height < 4096;
                
                item.offset = reader.position();
                item.size = QqtSizerHelper.calc(img.major, item.width, item.height);
                
                reader.seek(item.size, SeekType.CURRENT);
                
                if (img.major == 0) {
                    item.offset_alpha = reader.position();
                    item.size_alpha = item.width * item.height;
                    
                    reader.seek(item.size_alpha, SeekType.CURRENT);
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
    public boolean save(IPackage vfs, String path, IFullyWriter writer, QqtImg data) throws Exception {
        writer.writeIntEx(data.magic01);
        writer.writeIntEx(data.magic02);
        writer.writeShortEx(data.major);
        writer.writeShortEx(data.minor);
        writer.writeIntEx(data.headerSize);
        writer.writeIntEx(data.count);
        writer.writeIntEx(data.planes);
        writer.writeIntEx(data.offsetX);
        writer.writeIntEx(data.offsetY);
        writer.writeIntEx(data.maxWidth);
        writer.writeIntEx(data.maxHeight);
        
        for (QqtImgItem item : data.items) {
            writer.writeIntEx(item.reserved01);
            writer.writeIntEx(item.offsetX);
            writer.writeIntEx(item.offsetY);
            writer.writeIntEx(item.reserved04);
            if (item.reserved04 != 0) {
                writer.writeIntEx(item.width);
                writer.writeIntEx(item.height);
                writer.writeIntEx(item.reserved07);
            }
            
            // todo write image data
            
            if (data.major == 0) {
                // todo write image alpha data
            }
        }
        
        return true;
    }
}
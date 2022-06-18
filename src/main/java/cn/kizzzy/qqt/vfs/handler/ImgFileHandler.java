package cn.kizzzy.qqt.vfs.handler;

import cn.kizzzy.io.IFullyReader;
import cn.kizzzy.io.IFullyWriter;
import cn.kizzzy.io.SeekType;
import cn.kizzzy.qqt.ImgFile;
import cn.kizzzy.qqt.image.sizer.QqtSizerHelper;
import cn.kizzzy.vfs.IFileHandler;
import cn.kizzzy.vfs.IPackage;
import cn.kizzzy.vfs.stream.SliceInputStreamGetter;

public class ImgFileHandler implements IFileHandler<ImgFile> {
    
    @Override
    public ImgFile load(IPackage vfs, String path, IFullyReader reader, long size) throws Exception {
        ImgFile imgFile = new ImgFile();
        imgFile.magic01 = reader.readIntEx();
        imgFile.magic02 = reader.readIntEx();
        imgFile.major = reader.readShortEx();
        imgFile.minor = reader.readShortEx();
        imgFile.headerSize = reader.readIntEx();
        imgFile.count = reader.readIntEx();
        imgFile.planes = reader.readIntEx();
        imgFile.offsetX = reader.readIntEx();
        imgFile.offsetY = reader.readIntEx();
        imgFile.maxWidth = reader.readIntEx();
        imgFile.maxHeight = reader.readIntEx();
        imgFile.frames = new ImgFile.Frame[imgFile.count];
        for (int i = 0; i < imgFile.frames.length; ++i) {
            ImgFile.Frame frame = new ImgFile.Frame();
            frame.reserved01 = reader.readIntEx();
            frame.offsetX = reader.readIntEx();
            frame.offsetY = reader.readIntEx();
            frame.reserved04 = reader.readIntEx();
            if (frame.reserved04 != 0) {
                frame.width = reader.readIntEx();
                frame.height = reader.readIntEx();
                frame.reserved07 = reader.readIntEx();
            }
            
            long _size = QqtSizerHelper.calc(imgFile.major, frame.width, frame.height);
            
            frame.index = i;
            frame.file = imgFile;
            frame.pixels = new SliceInputStreamGetter(imgFile, reader.position(), _size);
            frame.valid = 0 < frame.width && frame.width < 4096 && 0 < frame.height && frame.height < 4096;
            
            reader.seek(_size, SeekType.CURRENT);
            
            if (imgFile.major == 0) {
                _size = (long) frame.width * frame.height;
                
                frame.alphas = new SliceInputStreamGetter(imgFile, reader.position(), _size);
                
                reader.seek(_size, SeekType.CURRENT);
            }
            
            imgFile.frames[i] = frame;
        }
        
        return imgFile;
    }
    
    @Override
    public boolean save(IPackage vfs, String path, IFullyWriter writer, ImgFile data) throws Exception {
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
        
        for (ImgFile.Frame item : data.frames) {
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

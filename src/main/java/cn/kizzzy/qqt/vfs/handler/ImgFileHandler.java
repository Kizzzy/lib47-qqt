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
        reader.setLittleEndian(true);
        
        ImgFile imgFile = new ImgFile();
        imgFile.magic01 = reader.readInt();
        imgFile.magic02 = reader.readInt();
        imgFile.major = reader.readShort();
        imgFile.minor = reader.readShort();
        imgFile.headerSize = reader.readInt();
        imgFile.count = reader.readInt();
        imgFile.planes = reader.readInt();
        imgFile.offsetX = reader.readInt();
        imgFile.offsetY = reader.readInt();
        imgFile.maxWidth = reader.readInt();
        imgFile.maxHeight = reader.readInt();
        imgFile.frames = new ImgFile.Frame[imgFile.count];
        for (int i = 0; i < imgFile.frames.length; ++i) {
            ImgFile.Frame frame = new ImgFile.Frame();
            frame.reserved01 = reader.readInt();
            frame.offsetX = reader.readInt();
            frame.offsetY = reader.readInt();
            frame.reserved04 = reader.readInt();
            if (frame.reserved04 != 0) {
                frame.width = reader.readInt();
                frame.height = reader.readInt();
                frame.reserved07 = reader.readInt();
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
        writer.setLittleEndian(true);
        
        writer.writeInt(data.magic01);
        writer.writeInt(data.magic02);
        writer.writeShort(data.major);
        writer.writeShort(data.minor);
        writer.writeInt(data.headerSize);
        writer.writeInt(data.count);
        writer.writeInt(data.planes);
        writer.writeInt(data.offsetX);
        writer.writeInt(data.offsetY);
        writer.writeInt(data.maxWidth);
        writer.writeInt(data.maxHeight);
        
        for (ImgFile.Frame item : data.frames) {
            writer.writeInt(item.reserved01);
            writer.writeInt(item.offsetX);
            writer.writeInt(item.offsetY);
            writer.writeInt(item.reserved04);
            if (item.reserved04 != 0) {
                writer.writeInt(item.width);
                writer.writeInt(item.height);
                writer.writeInt(item.reserved07);
            }
            
            // todo write image data
            
            if (data.major == 0) {
                // todo write image alpha data
            }
        }
        
        return true;
    }
}

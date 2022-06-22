package cn.kizzzy.qqt.vfs.handler;

import cn.kizzzy.io.IFullyReader;
import cn.kizzzy.io.IFullyWriter;
import cn.kizzzy.qqt.MapElemProp;
import cn.kizzzy.vfs.IFileHandler;
import cn.kizzzy.vfs.IPackage;

public class MapElemPropHandler implements IFileHandler<MapElemProp> {
    
    @Override
    public MapElemProp load(IPackage vfs, String path, IFullyReader reader, long size) throws Exception {
        reader.setLittleEndian(true);
        
        MapElemProp prop = new MapElemProp();
        prop.version = reader.readInt();
        prop.elements = new MapElemProp.Element[reader.readInt()];
        for (int i = 0, m = prop.elements.length; i < m; ++i) {
            MapElemProp.Element element = new MapElemProp.Element();
            element.id = reader.readInt();
            element.width = reader.readShort();
            element.height = reader.readShort();
            element.x = reader.readShort();
            element.y = reader.readShort();
            element.life = reader.readInt();
            element.level = reader.readInt();
            element.special = reader.readInt();
            element.attrs = new int[element.width * element.height];
            for (int j = 0, n = element.attrs.length; j < n; ++j) {
                element.attrs[j] = reader.readInt();
            }
            
            prop.elements[i] = element;
        }
        return prop;
    }
    
    @Override
    public boolean save(IPackage vfs, String path, IFullyWriter writer, MapElemProp data) throws Exception {
        writer.setLittleEndian(true);
        
        writer.writeInt(data.version);
        writer.writeInt(data.elements.length);
        for (MapElemProp.Element element : data.elements) {
            writer.writeInt(element.id);
            writer.writeShort(element.width);
            writer.writeShort(element.height);
            writer.writeShort(element.x);
            writer.writeShort(element.y);
            writer.writeInt(element.life);
            writer.writeInt(element.level);
            writer.writeInt(element.special);
            for (int attr : element.attrs) {
                writer.writeInt(attr);
            }
        }
        
        return true;
    }
}

package cn.kizzzy.qqt.vfs.handler;

import cn.kizzzy.io.IFullyReader;
import cn.kizzzy.io.IFullyWriter;
import cn.kizzzy.qqt.MapFile;
import cn.kizzzy.vfs.IFileHandler;
import cn.kizzzy.vfs.IPackage;

public class MapFileHandler implements IFileHandler<MapFile> {
    
    @Override
    public MapFile load(IPackage vfs, String path, IFullyReader reader, long size) throws Exception {
        reader.setLittleEndian(true);
        
        MapFile mapFile = new MapFile();
        mapFile.version = reader.readInt();
        mapFile.gameMode = reader.readInt();
        mapFile.maxPlayer = reader.readInt();
        mapFile.width = mapFile.version == 4 ? reader.readInt() : 15;
        mapFile.height = mapFile.version == 4 ? reader.readInt() : 13;
        
        mapFile.layers = new MapFile.Layer[3];
        for (int i = 0, n = mapFile.layers.length; i < n; ++i) {
            MapFile.Layer layer = new MapFile.Layer();
            layer.elements = new MapFile.Element[mapFile.height][mapFile.width];
            for (int r = 0, row = mapFile.height; r < row; ++r) {
                for (int c = 0, col = mapFile.width; c < col; ++c) {
                    MapFile.Element element = new MapFile.Element();
                    element.value = reader.readInt();
                    
                    layer.elements[r][c] = element;
                }
            }
            mapFile.layers[i] = layer;
        }
        
        mapFile.drops = new MapFile.Drop[reader.readInt()];
        for (int i = 0, n = mapFile.drops.length; i < n; ++i) {
            MapFile.Drop drop = new MapFile.Drop();
            drop.id = reader.readInt();
            drop.min = reader.readInt();
            drop.max = reader.readInt();
            drop.rate = reader.readFloat();
            
            mapFile.drops[i] = drop;
        }
        
        mapFile.points = new MapFile.Points[4];
        for (int i = 0; i < 4; ++i) {
            MapFile.Points points = new MapFile.Points();
            points.points = new MapFile.Point[reader.readInt()];
            for (int j = 0, n = points.points.length; j < n; ++j) {
                MapFile.Point point = new MapFile.Point();
                point.y = reader.readShort();
                point.x = reader.readShort();
                
                points.points[j] = point;
            }
            
            mapFile.points[i] = points;
        }
        
        return mapFile;
    }
    
    @Override
    public boolean save(IPackage vfs, String path, IFullyWriter writer, MapFile data) throws Exception {
        writer.setLittleEndian(true);
        
        writer.writeInt(data.version);
        writer.writeInt(data.gameMode);
        writer.writeInt(data.maxPlayer);
        if (data.version == 4) {
            writer.writeInt(data.width);
            writer.writeInt(data.height);
        }
        
        for (MapFile.Layer layer : data.layers) {
            for (int r = 0, row = data.height; r < row; ++r) {
                for (int c = 0, col = data.width; c < col; ++c) {
                    MapFile.Element element = layer.elements[r][c];
                    writer.writeInt(element.value);
                }
            }
        }
        
        writer.writeInt(data.drops.length);
        for (MapFile.Drop drop : data.drops) {
            writer.writeInt(drop.id);
            writer.writeInt(drop.min);
            writer.writeInt(drop.max);
            writer.writeFloat(drop.rate);
        }
        
        for (MapFile.Points points : data.points) {
            writer.writeInt(points.points.length);
            for (MapFile.Point point : points.points) {
                writer.writeShort(point.x);
                writer.writeShort(point.y);
            }
        }
        
        return true;
    }
}

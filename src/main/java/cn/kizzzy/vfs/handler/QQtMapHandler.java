package cn.kizzzy.vfs.handler;

import cn.kizzzy.io.FullyReader;
import cn.kizzzy.io.FullyWriter;
import cn.kizzzy.qqt.QqtMap;
import cn.kizzzy.vfs.IFileHandler;
import cn.kizzzy.vfs.IPackage;

public class QQtMapHandler implements IFileHandler<QqtMap> {
    
    @Override
    public QqtMap load(IPackage vfs, String path, FullyReader reader, long size) throws Exception {
        QqtMap map = new QqtMap();
        map.version = reader.readIntEx();
        map.gameMode = reader.readIntEx();
        map.maxPlayer = reader.readIntEx();
        map.width = map.version == 4 ? reader.readIntEx() : 15;
        map.height = map.version == 4 ? reader.readIntEx() : 13;
        
        map.layers = new QqtMap.Layer[3];
        for (int i = 0, n = map.layers.length; i < n; ++i) {
            QqtMap.Layer layer = new QqtMap.Layer();
            layer.elements = new QqtMap.Element[map.height][map.width];
            for (int r = 0, row = map.height; r < row; ++r) {
                for (int c = 0, col = map.width; c < col; ++c) {
                    QqtMap.Element element = new QqtMap.Element();
                    element.value = reader.readIntEx();
                    
                    layer.elements[r][c] = element;
                }
            }
            map.layers[i] = layer;
        }
        
        map.drops = new QqtMap.Drop[reader.readIntEx()];
        for (int i = 0, n = map.drops.length; i < n; ++i) {
            QqtMap.Drop drop = new QqtMap.Drop();
            drop.id = reader.readIntEx();
            drop.min = reader.readIntEx();
            drop.max = reader.readIntEx();
            drop.rate = reader.readFloatEx();
            
            map.drops[i] = drop;
        }
        
        map.points = new QqtMap.Points[4];
        for (int i = 0; i < 4; ++i) {
            QqtMap.Points points = new QqtMap.Points();
            points.points = new QqtMap.Point[reader.readIntEx()];
            for (int j = 0, n = points.points.length; j < n; ++j) {
                QqtMap.Point point = new QqtMap.Point();
                point.y = reader.readShortEx();
                point.x = reader.readShortEx();
                
                points.points[j] = point;
            }
            
            map.points[i] = points;
        }
        
        return map;
    }
    
    @Override
    public boolean save(IPackage vfs, String path, FullyWriter writer, QqtMap map) throws Exception {
        writer.writeIntEx(map.version);
        writer.writeIntEx(map.gameMode);
        writer.writeIntEx(map.maxPlayer);
        if (map.version == 4) {
            writer.writeIntEx(map.width);
            writer.writeIntEx(map.height);
        }
        
        for (QqtMap.Layer layer : map.layers) {
            for (int r = 0, row = map.height; r < row; ++r) {
                for (int c = 0, col = map.width; c < col; ++c) {
                    QqtMap.Element element = layer.elements[r][c];
                    writer.writeIntEx(element.value);
                }
            }
        }
        
        writer.writeIntEx(map.drops.length);
        for (QqtMap.Drop drop : map.drops) {
            writer.writeIntEx(drop.id);
            writer.writeIntEx(drop.min);
            writer.writeIntEx(drop.max);
            writer.writeFloatEx(drop.rate);
        }
        
        for (QqtMap.Points points : map.points) {
            writer.writeIntEx(points.points.length);
            for (QqtMap.Point point : points.points) {
                writer.writeShort(point.x);
                writer.writeShort(point.y);
            }
        }
        
        return true;
    }
}

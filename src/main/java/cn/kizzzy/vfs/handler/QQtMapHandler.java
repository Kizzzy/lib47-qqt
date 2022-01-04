package cn.kizzzy.vfs.handler;

import cn.kizzzy.io.DataOutputStreamEx;
import cn.kizzzy.io.SubStream;
import cn.kizzzy.qqt.QqtMap;
import cn.kizzzy.vfs.IPackage;

import java.util.LinkedList;

public class QQtMapHandler extends StreamFileHandler<QqtMap> {
    
    @Override
    protected QqtMap loadImpl(IPackage pack, String path, SubStream reader) throws Exception {
        QqtMap map = new QqtMap();
        map.version = reader.readIntEx();
        map.reserved01 = reader.readIntEx();
        map.reserved02 = reader.readIntEx();
        map.width = map.version == 4 ? reader.readIntEx() : 15;
        map.height = map.version == 4 ? reader.readIntEx() : 13;
        
        map.layers = new LinkedList<>();
        for (int i = 0; i < 3; ++i) {
            int row = map.height;
            int col = map.width;
            
            int[][] layerData = new int[row][col];
            for (int r = 0; r < row; ++r) {
                for (int c = 0; c < col; ++c) {
                    layerData[r][c] = reader.readIntEx();
                }
            }
            
            map.layers.add(layerData);
        }
        
        map.unknownCount = reader.readIntEx();
        map.unknowns = new QqtMap.Unknown[map.unknownCount];
        for (int i = 0; i < map.unknownCount; ++i) {
            QqtMap.Unknown unknown = new QqtMap.Unknown();
            unknown.reserved01 = reader.readIntEx();
            unknown.reserved02 = reader.readIntEx();
            unknown.reserved03 = reader.readIntEx();
            unknown.reserved04 = reader.readIntEx();
            map.unknowns[i] = unknown;
        }
        
        map.points = new QqtMap.Points[4];
        for (int i = 0; i < 4; ++i) {
            QqtMap.Points points = new QqtMap.Points();
            points.count = reader.readIntEx();
            points.points = new QqtMap.Point[points.count];
            for (int j = 0; j < points.count; ++j) {
                QqtMap.Point point = new QqtMap.Point();
                point.x = reader.readShortEx();
                point.y = reader.readShortEx();
                points.points[j] = point;
            }
            
            map.points[i] = points;
        }
        
        return map;
    }
    
    @Override
    protected void saveImpl(DataOutputStreamEx writer, QqtMap data) throws Exception {
        throw new UnsupportedOperationException("not support");
    }
}

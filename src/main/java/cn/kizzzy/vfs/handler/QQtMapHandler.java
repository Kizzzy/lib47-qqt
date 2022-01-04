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
            int row = 13;
            int col = 15;
            
            int[][] layerData = new int[row][col];
            
            for (int r = 0; r < row; ++r) {
                for (int c = 0; c < col; ++c) {
                    layerData[r][c] = reader.readIntEx();
                }
            }
            
            map.layers.add(layerData);
        }
        /*
        map.pointCount0 = reader.readIntEx();
        
        map.pointCount1 = reader.readIntEx();
        map.points1 = new QqtMap.Point[map.pointCount1];
        for (int i = 0; i < map.pointCount1; ++i) {
            QqtMap.Point point = new QqtMap.Point();
            point.x = reader.readShortEx();
            point.y = reader.readShortEx();
            
            map.points1[i] = point;
        }
        
        map.pointCount2 = reader.readIntEx();
        map.points2 = new QqtMap.Point[map.pointCount2];
        for (int i = 0; i < map.pointCount2; ++i) {
            QqtMap.Point point = new QqtMap.Point();
            point.x = reader.readShortEx();
            point.y = reader.readShortEx();
            map.points2[i] = point;
        }
        
        map.pointCount3 = reader.readIntEx();
        map.points3 = new QqtMap.Point[map.pointCount3];
        for (int i = 0; i < map.pointCount3; ++i) {
            QqtMap.Point point = new QqtMap.Point();
            point.x = reader.readShortEx();
            point.y = reader.readShortEx();
            map.points3[i] = point;
        }*/
        return map;
    }
    
    @Override
    protected void saveImpl(DataOutputStreamEx writer, QqtMap data) throws Exception {
        throw new UnsupportedOperationException("not support");
    }
}

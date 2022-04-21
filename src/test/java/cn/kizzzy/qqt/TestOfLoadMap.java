package cn.kizzzy.qqt;

import cn.kizzzy.vfs.IPackage;
import cn.kizzzy.vfs.handler.QQtMapHandler;
import cn.kizzzy.vfs.pack.FilePackage;
import cn.kizzzy.vfs.tree.FileTreeBuilder;

public class TestOfLoadMap {
    
    public static void main(String[] args) {
        String loadRoot = "E:\\04Games\\Tencent\\QQ堂";
        
        IPackage loadVfs = new FilePackage(loadRoot, new FileTreeBuilder(loadRoot).build());
        loadVfs.getHandlerKvs().put(QqtMap.class, new QQtMapHandler());
        
        QqtMap map = loadVfs.load("map/box06_6.map", QqtMap.class);
        System.out.println(map);
    }
}

package cn.kizzzy.vfs.pack;

import cn.kizzzy.qqt.QqtImg;
import cn.kizzzy.qqt.QqtMap;
import cn.kizzzy.vfs.ITree;
import cn.kizzzy.vfs.handler.QQtMapHandler;
import cn.kizzzy.vfs.handler.QqtImgHandler;

public class QqtPackage extends IdxPackage {
    
    public QqtPackage(String root, ITree tree) {
        super(root, tree);
    }
    
    @Override
    protected void initDefaultHandler() {
        super.initDefaultHandler();
        
        handlerKvs.put(QqtImg.class, new QqtImgHandler());
        handlerKvs.put(QqtMap.class, new QQtMapHandler());
    }
    
    @Override
    protected String dealWithPkgName(String pkg) {
        return pkg.replace("idx", "pkg");
    }
}

package cn.kizzzy.qqt.vfs.pack;

import cn.kizzzy.qqt.MapElemProp;
import cn.kizzzy.qqt.QqtAvatar;
import cn.kizzzy.qqt.QqtImg;
import cn.kizzzy.qqt.QqtMap;
import cn.kizzzy.qqt.vfs.handler.MapElemPropHandler;
import cn.kizzzy.qqt.vfs.handler.QQtMapHandler;
import cn.kizzzy.qqt.vfs.handler.QqtAvatarHandler;
import cn.kizzzy.qqt.vfs.handler.QqtImgHandler;
import cn.kizzzy.tencent.vfs.pack.IdxPackage;
import cn.kizzzy.vfs.ITree;

public class QqtPackage extends IdxPackage {
    
    public QqtPackage(String root, ITree tree) {
        super(root, tree);
    }
    
    @Override
    protected void initDefaultHandler() {
        super.initDefaultHandler();
        
        handlerKvs.put(QqtImg.class, new QqtImgHandler());
        handlerKvs.put(QqtMap.class, new QQtMapHandler());
        handlerKvs.put(QqtAvatar.class, new QqtAvatarHandler());
        handlerKvs.put(MapElemProp.class, new MapElemPropHandler());
    }
    
    @Override
    protected String dealWithPkgName(String pkg) {
        return pkg.replace("idx", "pkg");
    }
}

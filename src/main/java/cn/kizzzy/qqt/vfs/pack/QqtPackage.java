package cn.kizzzy.qqt.vfs.pack;

import cn.kizzzy.qqt.AvatarFile;
import cn.kizzzy.qqt.ImgFile;
import cn.kizzzy.qqt.MapElemProp;
import cn.kizzzy.qqt.MapFile;
import cn.kizzzy.qqt.vfs.handler.AvatarFileHandler;
import cn.kizzzy.qqt.vfs.handler.ImgFileHandler;
import cn.kizzzy.qqt.vfs.handler.MapElemPropHandler;
import cn.kizzzy.qqt.vfs.handler.MapFileHandler;
import cn.kizzzy.tencent.vfs.pack.IdxPackage;
import cn.kizzzy.vfs.IStreamGetterFactory;
import cn.kizzzy.vfs.ITree;
import cn.kizzzy.vfs.stream.FileStreamGetterFactory;

public class QqtPackage extends IdxPackage {
    
    public QqtPackage(String root, ITree tree) {
        this(tree, new FileStreamGetterFactory(root));
    }
    
    public QqtPackage(ITree tree, IStreamGetterFactory factory) {
        super(tree, factory, "idx");
    }
    
    @Override
    protected void initDefaultHandler() {
        super.initDefaultHandler();
        
        addHandler(ImgFile.class, new ImgFileHandler());
        addHandler(MapFile.class, new MapFileHandler());
        addHandler(AvatarFile.class, new AvatarFileHandler());
        addHandler(MapElemProp.class, new MapElemPropHandler());
    }
}

package cn.kizzzy.qqt;

import cn.kizzzy.vfs.IPackage;
import cn.kizzzy.vfs.handler.MapElemPropHandler;
import cn.kizzzy.vfs.pack.FilePackage;
import cn.kizzzy.vfs.tree.FileTreeBuilder;

public class TestOfLoadMapElemProp {
    
    public static void main(String[] args) {
        String loadRoot = "E:\\04Games\\Tencent\\QQ堂";
        
        IPackage loadVfs = new FilePackage(loadRoot, new FileTreeBuilder(loadRoot).build());
        loadVfs.getHandlerKvs().put(MapElemProp.class, new MapElemPropHandler());
        
        MapElemProp prop = loadVfs.load("object\\mapElem\\mapElem.prop", MapElemProp.class);
        System.out.println(prop);
    }
}

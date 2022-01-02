package cn.kizzzy;

import cn.kizzzy.qqt.QqtFile;
import cn.kizzzy.qqt.QqtIdx;
import cn.kizzzy.qqt.QqtImg;
import cn.kizzzy.vfs.IPackage;
import cn.kizzzy.vfs.ITree;
import cn.kizzzy.vfs.handler.BufferedImageHandler;
import cn.kizzzy.vfs.handler.QqtIdxFileHandler;
import cn.kizzzy.vfs.handler.QqtImgHandler;
import cn.kizzzy.vfs.pack.FilePackage;
import cn.kizzzy.vfs.pack.QqtPackage;
import cn.kizzzy.vfs.tree.LocalTree;
import cn.kizzzy.vfs.tree.QqtTreeHelper;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class MainOfQqtIdx {
    
    public static void main(String[] args) throws IOException {
        IPackage tempKvs = new FilePackage("E:\\88Extrator\\qqt\\pkg");
        tempKvs.getHandlerKvs().put(BufferedImage.class, new BufferedImageHandler());
        
        IPackage qqtVfs = new FilePackage("E:\\04Games\\Tencent\\QQ堂\\data");
        qqtVfs.getHandlerKvs().put(QqtImg.class, new QqtImgHandler());
        qqtVfs.getHandlerKvs().put(QqtIdx.class, new QqtIdxFileHandler());
        
        QqtIdx idx = qqtVfs.load("object.idx", QqtIdx.class);
        if (idx != null) {
            ITree<QqtFile> tree = new LocalTree<>(QqtTreeHelper.ToPack(idx));
            IPackage pkgVfs = new QqtPackage("E:\\04Games\\Tencent\\QQ堂\\data", tree);
            
            idx.fileKvs.forEach((path, file) -> {
                System.out.println(file);
                
                byte[] bytes = pkgVfs.load(file.path, byte[].class);
                tempKvs.save(file.path, bytes);
            });
        }
    }
}

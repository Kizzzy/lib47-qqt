package cn.kizzzy;

import cn.kizzzy.qqt.QqtImg;
import cn.kizzzy.qqt.QqtImgHelper;
import cn.kizzzy.qqt.QqtImgItem;
import cn.kizzzy.vfs.IPackage;
import cn.kizzzy.vfs.ListNode;
import cn.kizzzy.vfs.ListParameter;
import cn.kizzzy.vfs.handler.BufferedImageHandler;
import cn.kizzzy.vfs.handler.QqtImgHandler;
import cn.kizzzy.vfs.pack.FilePackage;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class MainOfQqtPkg {
    
    public static void main(String[] args) throws IOException {
        IPackage tempKvs = new FilePackage("E:\\88Extrator\\qqt\\pkg_extra");
        tempKvs.getHandlerKvs().put(BufferedImage.class, new BufferedImageHandler());
        
        IPackage qqtVfs = new FilePackage("E:\\88Extrator\\qqt\\pkg");
        qqtVfs.getHandlerKvs().put(QqtImg.class, new QqtImgHandler());
        
        ListNode root = qqtVfs.list("", new ListParameter(true));
        
        listImpl(root, tempKvs, qqtVfs);
    }
    
    private static void listImpl(ListNode node, IPackage tempKvs, IPackage qqtVfs) {
        if (node.children != null && node.children.size() > 0) {
            for (ListNode child : node.children) {
                listImpl(child, tempKvs, qqtVfs);
            }
        } else if (node.path.endsWith(".img")) {
            QqtImg img = qqtVfs.load(node.path, QqtImg.class);
            if (img != null) {
                for (QqtImgItem item : img.items) {
                    if (item != null) {
                        BufferedImage image = QqtImgHelper.toImage(item);
                        if (image != null) {
                            String fullPath = node.path.replace(".img", "-" + item.index + ".png");
                            tempKvs.save(fullPath, image);
                        }
                    }
                }
            }
        }
    }
}

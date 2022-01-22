package cn.kizzzy.qqt;

import cn.kizzzy.helper.LogHelper;
import cn.kizzzy.qqt.helper.QqtImgHelper;
import cn.kizzzy.vfs.IPackage;
import cn.kizzzy.vfs.ITree;
import cn.kizzzy.vfs.handler.BufferedImageHandler;
import cn.kizzzy.vfs.handler.QqtIdxFileHandler;
import cn.kizzzy.vfs.handler.QqtImgHandler;
import cn.kizzzy.vfs.pack.FilePackage;
import cn.kizzzy.vfs.pack.QqtPackage;
import cn.kizzzy.vfs.tree.IdGenerator;
import cn.kizzzy.vfs.tree.Leaf;
import cn.kizzzy.vfs.tree.Node;
import cn.kizzzy.vfs.tree.NodeComparator;
import cn.kizzzy.vfs.tree.QqtTreeBuilder;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ExportImageFromPkgTest {
    
    public static void main(String[] args) throws IOException {
        String exportRoot = "E:\\88Extrator\\QQT\\Export\\object";
        String dataRoot = "E:\\04Games\\Tencent\\QQ堂\\data";
        
        IPackage exportVfs = new FilePackage(exportRoot);
        exportVfs.getHandlerKvs().put(BufferedImage.class, new BufferedImageHandler());
        
        IPackage dataVfs = new FilePackage(dataRoot);
        dataVfs.getHandlerKvs().put(QqtImg.class, new QqtImgHandler());
        dataVfs.getHandlerKvs().put(QqtIdx.class, new QqtIdxFileHandler());
        
        QqtIdx idx = dataVfs.load("object.idx", QqtIdx.class);
        if (idx == null) {
            LogHelper.error("load idx failed");
            return;
        }
        
        ITree tree = new QqtTreeBuilder(idx, new IdGenerator()).build();
        
        IPackage pkgVfs = new QqtPackage(dataRoot, tree);
        
        List<Node> nodes = tree.listNode("");
        for (Node node : nodes) {
            listNodeImpl(node, pkgVfs, exportVfs);
        }
    }
    
    private static void listNodeImpl(Node node, IPackage pkgVfs, IPackage exportVfs) {
        if (node.leaf) {
            Leaf leaf = (Leaf) node;
            if (leaf.path.contains(".img")) {
                System.out.println("export: " + leaf.path);
                
                QqtImg img = pkgVfs.load(leaf.path, QqtImg.class);
                if (img != null) {
                    for (QqtImgItem item : img.items) {
                        if (item != null) {
                            BufferedImage image = QqtImgHelper.toImage(item);
                            if (image != null) {
                                String fullPath = leaf.path.replace(".img", String.format("-%02d.png", item.index));
                                exportVfs.save(fullPath, image);
                            }
                        }
                    }
                }
            }
        } else {
            List<Node> list = new LinkedList<>(node.children.values());
            list.sort(new NodeComparator());
            
            for (Node child : list) {
                listNodeImpl(child, pkgVfs, exportVfs);
            }
        }
    }
}

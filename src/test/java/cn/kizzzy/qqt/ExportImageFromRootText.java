package cn.kizzzy.qqt;

import cn.kizzzy.vfs.IPackage;
import cn.kizzzy.vfs.ITree;
import cn.kizzzy.vfs.Separator;
import cn.kizzzy.vfs.handler.BufferedImageHandler;
import cn.kizzzy.vfs.handler.QqtImgHandler;
import cn.kizzzy.vfs.pack.FilePackage;
import cn.kizzzy.vfs.tree.FileTreeBuilder;
import cn.kizzzy.vfs.tree.IdGenerator;
import cn.kizzzy.vfs.tree.Leaf;
import cn.kizzzy.vfs.tree.LocalTree;
import cn.kizzzy.vfs.tree.Node;
import cn.kizzzy.vfs.tree.NodeComparator;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ExportImageFromRootText {
    
    public static void main(String[] args) throws IOException {
        String exportRoot = "E:\\88Extrator\\QQT\\Export";
        String qqtRoot = "E:\\04Games\\Tencent\\QQ堂";
        
        IPackage exportKvs = new FilePackage(exportRoot);
        exportKvs.getHandlerKvs().put(BufferedImage.class, new BufferedImageHandler());
        
        IPackage qqtVfs = new FilePackage(qqtRoot);
        qqtVfs.getHandlerKvs().put(QqtImg.class, new QqtImgHandler());
        
        ITree<Void> tree = new LocalTree<>(
            new FileTreeBuilder<Void>(qqtRoot, new IdGenerator()).build(),
            Separator.BACKSLASH_SEPARATOR_LOWERCASE
        );
        
        List<Node<Void>> nodes = tree.listNode("");
        for (Node<Void> node : nodes) {
            listNodeImpl(node, qqtVfs, exportKvs);
        }
    }
    
    private static void listNodeImpl(Node<Void> node, IPackage qqtVfs, IPackage exportVfs) {
        if (node.leaf) {
            Leaf<Void> leaf = (Leaf<Void>) node;
            if (leaf.path.endsWith(".img")) {
                System.out.println("export: " + leaf.path);
                
                QqtImg img = qqtVfs.load(leaf.path, QqtImg.class);
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
            List<Node<Void>> list = new LinkedList<>(node.children.values());
            list.sort(new NodeComparator<>());
            
            for (Node<Void> child : list) {
                listNodeImpl(child, qqtVfs, exportVfs);
            }
        }
    }
}

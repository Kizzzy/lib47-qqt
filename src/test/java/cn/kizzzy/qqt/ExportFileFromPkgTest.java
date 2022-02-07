package cn.kizzzy.qqt;

import cn.kizzzy.helper.LogHelper;
import cn.kizzzy.tencent.IdxFile;
import cn.kizzzy.vfs.IPackage;
import cn.kizzzy.vfs.ITree;
import cn.kizzzy.vfs.handler.IdxFileHandler;
import cn.kizzzy.vfs.handler.QqtImgHandler;
import cn.kizzzy.vfs.pack.FilePackage;
import cn.kizzzy.vfs.pack.QqtPackage;
import cn.kizzzy.vfs.tree.IdGenerator;
import cn.kizzzy.vfs.tree.IdxTreeBuilder;
import cn.kizzzy.vfs.tree.Leaf;
import cn.kizzzy.vfs.tree.Node;
import cn.kizzzy.vfs.tree.NodeComparator;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ExportFileFromPkgTest {
    
    public static void main(String[] args) throws IOException {
        String exportRoot = "E:\\88Extrator\\QQT\\Source";
        String dataRoot = "E:\\04Games\\Tencent\\QQ堂\\data";
        
        IPackage tempVfs = new FilePackage(exportRoot);
        
        IPackage dataVfs = new FilePackage(dataRoot);
        dataVfs.getHandlerKvs().put(QqtImg.class, new QqtImgHandler());
        dataVfs.getHandlerKvs().put(IdxFile.class, new IdxFileHandler());
        
        IdxFile idx = dataVfs.load("object.idx", IdxFile.class);
        if (idx == null) {
            LogHelper.error("load idx failed");
            return;
        }
        
        ITree tree = new IdxTreeBuilder(idx, new IdGenerator()).build();
        
        IPackage pkgVfs = new QqtPackage(dataRoot, tree);
        
        List<Node> list = tree.listNode("");
        for (Node node : list) {
            listNodeImpl(node, pkgVfs, tempVfs);
        }
    }
    
    private static void listNodeImpl(Node item, IPackage pkgVfs, IPackage tempVfs) {
        if (item.leaf) {
            Leaf leaf = (Leaf) item;
            System.out.println("export: " + leaf.path);
            
            byte[] data = pkgVfs.load(leaf.path, byte[].class);
            tempVfs.save(leaf.path, data);
        } else {
            List<Node> list = new LinkedList<>(item.children.values());
            list.sort(new NodeComparator());
            
            for (Node child : list) {
                listNodeImpl(child, pkgVfs, tempVfs);
            }
        }
    }
}

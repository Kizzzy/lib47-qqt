package cn.kizzzy.qqt;

import cn.kizzzy.helper.LogHelper;
import cn.kizzzy.vfs.IPackage;
import cn.kizzzy.vfs.ITree;
import cn.kizzzy.vfs.handler.QqtIdxFileHandler;
import cn.kizzzy.vfs.pack.FilePackage;
import cn.kizzzy.vfs.tree.IdGenerator;
import cn.kizzzy.vfs.tree.Node;
import cn.kizzzy.vfs.tree.NodeComparator;
import cn.kizzzy.vfs.tree.QqtTreeBuilder;

import java.util.List;

public class ListPkgTest {
    
    public static void main(String[] args) {
        String pkgName = "object";
        String dataRoot = "E:\\04Games\\Tencent\\QQ堂\\data";
        String[] paths = new String[]{
            "",
            "object",
            "body",
            "object/body",
            "object/body/body1_stand.img",
        };
        
        IPackage dataVfs = new FilePackage(dataRoot);
        dataVfs.getHandlerKvs().put(QqtIdx.class, new QqtIdxFileHandler());
        
        QqtIdx idx = dataVfs.load(pkgName + ".idx", QqtIdx.class);
        if (idx == null) {
            LogHelper.error("load idx failed: {}", pkgName);
            return;
        }
        
        ITree<QqtFile> tree = new QqtTreeBuilder(idx, new IdGenerator()).build();
        
        for (String path : paths) {
            listNodeImpl(tree, path);
        }
    }
    
    private static void listNodeImpl(ITree<QqtFile> tree, String path) {
        List<Node<QqtFile>> list = tree.listNode(path);
        list.sort(new NodeComparator<>());
        System.out.printf("path: %-32s, node count: %4d, list:", path, list.size());
        for (Node<QqtFile> item : list) {
            System.out.print(" " + item.name);
        }
        System.out.println();
    }
}

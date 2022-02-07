package cn.kizzzy.qqt;

import cn.kizzzy.helper.LogHelper;
import cn.kizzzy.tencent.IdxFile;
import cn.kizzzy.vfs.IPackage;
import cn.kizzzy.vfs.ITree;
import cn.kizzzy.vfs.handler.IdxFileHandler;
import cn.kizzzy.vfs.pack.FilePackage;
import cn.kizzzy.vfs.tree.IdGenerator;
import cn.kizzzy.vfs.tree.IdxTreeBuilder;
import cn.kizzzy.vfs.tree.Node;
import cn.kizzzy.vfs.tree.NodeComparator;

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
        dataVfs.getHandlerKvs().put(IdxFile.class, new IdxFileHandler());
        
        IdxFile idx = dataVfs.load(pkgName + ".idx", IdxFile.class);
        if (idx == null) {
            LogHelper.error("load idx failed: {}", pkgName);
            return;
        }
        
        ITree tree = new IdxTreeBuilder(idx, new IdGenerator()).build();
        
        for (String path : paths) {
            listNodeImpl(tree, path);
        }
    }
    
    private static void listNodeImpl(ITree tree, String path) {
        List<Node> list = tree.listNode(path);
        list.sort(new NodeComparator());
        System.out.printf("path: %-32s, node count: %4d, list:", path, list.size());
        for (Node item : list) {
            System.out.print(" " + item.name);
        }
        System.out.println();
    }
}

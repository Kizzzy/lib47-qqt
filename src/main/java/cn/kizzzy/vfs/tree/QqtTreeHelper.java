package cn.kizzzy.vfs.tree;


import cn.kizzzy.qqt.QqtFile;
import cn.kizzzy.qqt.QqtIdx;

public class QqtTreeHelper {
    
    public static Root<QqtFile> ToPack(QqtIdx idx) {
        Root<QqtFile> pack = new Root<>(idx.path);
        
        for (QqtFile file : idx.fileKvs.values()) {
            Leaf<QqtFile> file1 = new Leaf<>();
            file1.pack = idx.path;
            file1.path = file.path;
            file1.item = file;
            
            pack.fileKvs.put(file1.path, file1);
            
            ListFolder(pack, pack, file1);
        }
        
        return pack;
    }
    
    private static void ListFolder(Root<QqtFile> pack, Node<QqtFile> parent, Leaf<QqtFile> file) {
        String[] paths = file.path.split("\\\\");
        for (String path : paths) {
            Node<QqtFile> child = parent.children.get(path);
            if (child == null) {
                child = new Node<>(path, parent, path.contains(".") ? file : null);
                parent.children.put(path, child);
                
                pack.folderKvs.put(child.id, child);
            }
            parent = child;
        }
    }
}

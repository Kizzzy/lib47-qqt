package cn.kizzzy.vfs.pack;

import cn.kizzzy.io.FullyReader;
import cn.kizzzy.qqt.QqtFile;
import cn.kizzzy.qqt.QqtImg;
import cn.kizzzy.qqt.QqtMap;
import cn.kizzzy.vfs.IFileLoader;
import cn.kizzzy.vfs.IFileSaver;
import cn.kizzzy.vfs.IStreamable;
import cn.kizzzy.vfs.ITree;
import cn.kizzzy.vfs.handler.QQtMapHandler;
import cn.kizzzy.vfs.handler.QqtImgHandler;
import cn.kizzzy.vfs.streamable.FileStreamable;
import cn.kizzzy.vfs.tree.Leaf;

public class QqtPackage extends PackageAdapter {
    
    private final ITree<QqtFile> tree;
    
    public QqtPackage(String root, ITree<QqtFile> tree) {
        super(root);
        this.tree = tree;
    }
    
    @Override
    protected void initDefaultHandler() {
        super.initDefaultHandler();
        
        handlerKvs.put(QqtImg.class, new QqtImgHandler());
        handlerKvs.put(QqtMap.class, new QQtMapHandler());
    }
    
    @Override
    public boolean exist(String path) {
        return tree.getLeaf(path) != null;
    }
    
    @Override
    protected Object loadImpl(String path, IFileLoader<?> loader) throws Exception {
        Leaf<QqtFile> leaf = tree.getLeaf(path);
        if (leaf == null) {
            return null;
        }
        
        QqtFile file = leaf.item;
        if (file == null) {
            return null;
        }
        
        String fullPath = FILE_SEPARATOR.combine(root, file.pkg.replace("idx", "pkg"));
        if (file.getSource() == null) {
            file.setSource(new FileStreamable(fullPath));
        }
        
        try (FullyReader reader = file.OpenStream()) {
            Object obj = loader.load(this, path, reader, file.originSize);
            if (obj instanceof IStreamable) {
                ((IStreamable) obj).setSource(file);
            }
            return obj;
        }
    }
    
    @Override
    protected <T> boolean saveImpl(String path, T data, IFileSaver<T> saver) throws Exception {
        return false;
    }
}

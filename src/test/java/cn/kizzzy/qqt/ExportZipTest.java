package cn.kizzzy.qqt;

import cn.kizzzy.helper.LogHelper;
import cn.kizzzy.vfs.IPackage;
import cn.kizzzy.vfs.ITree;
import cn.kizzzy.vfs.pack.FilePackage;
import cn.kizzzy.vfs.pack.ZipPackage;
import cn.kizzzy.vfs.tree.FileTreeBuilder;
import cn.kizzzy.vfs.tree.Leaf;
import cn.kizzzy.vfs.tree.ZipTreeBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExportZipTest {
    
    public static void main(String[] args) {
        String zipRoot = "E:\\05Soft\\dl_save\\games\\QQ堂\\zip";
        String exportRoot = "E:\\05Soft\\dl_save\\games\\QQ堂\\zip\\Export";
        
        IPackage exportVfs = new FilePackage(exportRoot);
        
        ITree fileTree = new FileTreeBuilder(zipRoot).build();
        List<Leaf> nodes = fileTree.listLeaf(0);
        
        for (Leaf leaf : nodes) {
            if (leaf.name.contains(".zip")) {
                ITree zipTree = new ZipTreeBuilder(zipRoot + "\\" + leaf.name).build();
                if (zipTree == null) {
                    continue;
                }
                
                Leaf fileInfoLeaf = null;
                Map<String, Leaf> leafKvs = new HashMap<>();
                
                List<Leaf> leaves = zipTree.listLeaf(0, true);
                for (Leaf temp : leaves) {
                    leafKvs.put(temp.name.toLowerCase(), temp);     // use lowercase
                    
                    if (temp.name.contains(".ini")) {
                        fileInfoLeaf = temp;
                    }
                }
                
                if (fileInfoLeaf != null) {
                    ZipPackage zipVfs = new ZipPackage(zipRoot + "\\" + leaf.name);
                    
                    String text = zipVfs.load(fileInfoLeaf.path, String.class);
                    if (text != null) {
                        Map<String, String> kvs = new HashMap<>();
                        String[] lines = text.split("\r\n");
                        for (String line : lines) {
                            if (line.contains("=")) {
                                String[] kv = line.split("=");
                                kvs.put(kv[0], kv[1]);
                            }
                        }
                        
                        String countStr = kvs.get("count");
                        if (countStr != null) {
                            try {
                                int count = Integer.parseInt(countStr);
                                for (int i = 0; i < count; ++i) {
                                    String src = kvs.get("src" + (i + 1));
                                    String dst = kvs.get("dst" + (i + 1));
                                    
                                    Leaf target = leafKvs.get(src.toLowerCase());     // use lowercase
                                    if (target == null) {
                                        LogHelper.info(leaf.name + " - " + src);
                                        continue;
                                    }
                                    
                                    byte[] data = zipVfs.load(target.path, byte[].class);
                                    exportVfs.save(dst, data);
                                }
                            } catch (Exception e) {
                                LogHelper.error("export error", e);
                            }
                        }
                    }
                }
            }
        }
    }
}

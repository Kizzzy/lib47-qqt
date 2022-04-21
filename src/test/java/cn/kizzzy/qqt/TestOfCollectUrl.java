package cn.kizzzy.qqt;

import cn.kizzzy.vfs.IPackage;
import cn.kizzzy.vfs.pack.FilePackage;
import cn.kizzzy.vfs.tree.FileTreeBuilder;
import cn.kizzzy.vfs.tree.Node;

import java.util.List;

public class TestOfCollectUrl {
    
    public static void main(String[] args) {
        String loadRoot = "E:\\04Games\\Tencent\\QQ堂\\object\\player";
        String saveRoot = "F:\\05Soft\\dd_games\\QQ堂";
        
        IPackage loadVfs = new FilePackage(loadRoot, new FileTreeBuilder(loadRoot).build());
        
        StringBuilder builder = new StringBuilder();
        
        List<Node> nodes = loadVfs.listNode("");
        for (Node node : nodes) {
            listNodeImpl(node, loadVfs, builder);
        }
        
        IPackage saveVfs = new FilePackage(saveRoot);
        saveVfs.save("qqt-dl-3.txt", builder.toString());
    }
    
    private static void listNodeImpl(Node node, IPackage dataVfs, StringBuilder builder) {
        if (node.leaf) {
            if (node.name.startsWith("player") && node.name.endsWith(".ini")) {
                String content = dataVfs.load(node.name, String.class);
                if (content != null) {
                    String[] lines = content.split("\r\n");
                    
                    String action = null;
                    for (String line : lines) {
                        if (line.startsWith("[") && line.endsWith("]")) {
                            action = line.substring(1, line.length() - 1);
                        }
                        
                        int index = line.indexOf("=");
                        if (index > 0) {
                            String element = line.substring(0, index).trim();
                            String id = line.substring(index + 1).trim();
                            
                            String url = String.format(
                                "https://qqt-img.qq.com/item/ItemZips/%s/%s%s.zip",
                                element,
                                element,
                                id
                            );
                            builder.append(url).append("\r\n");
                        }
                    }
                }
            }
        } else {
            for (Node child : node.children.values()) {
                listNodeImpl(child, dataVfs, builder);
            }
        }
    }
}

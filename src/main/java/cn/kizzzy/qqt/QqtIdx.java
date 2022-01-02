package cn.kizzzy.qqt;

import java.util.HashMap;
import java.util.Map;

public class QqtIdx {
    public int reserved01;
    public int totalCount;
    public int reserved03;
    public int reserved04;
    public final Map<String, QqtFile> fileKvs
        = new HashMap<>();
    
    public String path;
}

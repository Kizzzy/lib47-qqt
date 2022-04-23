package cn.kizzzy.qqt;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class QqtAvatarElementComparator implements Comparator<QqtAvatar.Element> {
    
    private static final Map<String, Integer> priorityKvs
        = new HashMap<>();
    
    static {
        priorityKvs.put("npack", 0);
        priorityKvs.put("fpack", 1);
        priorityKvs.put("ear", 2);
        priorityKvs.put("bhadorn", 3);
        priorityKvs.put("cap", 4);
        priorityKvs.put("fhadorn", 5);
        priorityKvs.put("hair", 6);
        priorityKvs.put("face", 7);
        priorityKvs.put("head", 8);
        priorityKvs.put("neck", 9);
        priorityKvs.put("cladorn", 10);
        priorityKvs.put("cloth", 11);
        priorityKvs.put("body", 12);
        priorityKvs.put("shose", 13);
        priorityKvs.put("leg", 14);
        priorityKvs.put("foot", 15);
        priorityKvs.put("mouth", 16);
        priorityKvs.put("mask", 17);
        priorityKvs.put("eye", 18);
    }
    
    @Override
    public int compare(QqtAvatar.Element o1, QqtAvatar.Element o2) {
        int p1 = priorityKvs.computeIfAbsent(o1.name, k -> 0);
        int p2 = priorityKvs.computeIfAbsent(o2.name, k -> 0);
        return p2 - p1;
    }
}

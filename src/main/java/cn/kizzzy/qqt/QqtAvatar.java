package cn.kizzzy.qqt;

import java.util.HashMap;
import java.util.Map;

public class QqtAvatar {
    
    public Map<String, Avatar> avatarKvs
        = new HashMap<>();
    
    public static class Avatar {
        public Map<String, Element> elementKvs
            = new HashMap<>();
    }
    
    public static class Element {
        public String name;
        public String id;
    }
}

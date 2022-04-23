package cn.kizzzy.vfs.handler;

import cn.kizzzy.qqt.QqtAvatar;

public class QqtAvatarHandler extends TextFileHandler<QqtAvatar> {
    
    @Override
    protected QqtAvatar loadImpl(String s) {
        QqtAvatar qqtAvatar = new QqtAvatar();
        
        String[] lines = s.split("\r\n");
        QqtAvatar.Avatar avatar = null;
        for (String line : lines) {
            if (line.startsWith("[") && line.endsWith("]")) {
                String action = line.substring(1, line.length() - 1).trim();
                avatar = new QqtAvatar.Avatar();
                qqtAvatar.avatarKvs.put(action, avatar);
                continue;
            }
            
            int index = line.indexOf("=");
            if (index >= 0 && avatar != null) {
                QqtAvatar.Element element = new QqtAvatar.Element();
                element.name = line.substring(0, index).trim();
                element.id = line.substring(index + 1).trim();
                
                avatar.elementKvs.put(element.name, element);
            }
        }
        
        return qqtAvatar;
    }
    
    @Override
    protected String saveImpl(QqtAvatar qqtAvatar) {
        return null;
    }
}

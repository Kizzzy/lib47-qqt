package cn.kizzzy.qqt.vfs.handler;

import cn.kizzzy.qqt.AvatarFile;
import cn.kizzzy.vfs.handler.TextFileHandler;

public class AvatarFileHandler extends TextFileHandler<AvatarFile> {
    
    @Override
    protected AvatarFile loadImpl(String s) {
        AvatarFile avatarFile = new AvatarFile();
        
        String[] lines = s.split("\r\n");
        AvatarFile.Avatar avatar = null;
        for (String line : lines) {
            if (line.startsWith("[") && line.endsWith("]")) {
                String action = line.substring(1, line.length() - 1).trim();
                avatar = new AvatarFile.Avatar();
                avatarFile.avatarKvs.put(action, avatar);
                continue;
            }
            
            int index = line.indexOf("=");
            if (index >= 0 && avatar != null) {
                AvatarFile.Element element = new AvatarFile.Element();
                element.name = line.substring(0, index).trim();
                element.id = line.substring(index + 1).trim();
                
                avatar.elementKvs.put(element.name, element);
            }
        }
        
        return avatarFile;
    }
    
    @Override
    protected String saveImpl(AvatarFile avatarFile) {
        return null;
    }
}

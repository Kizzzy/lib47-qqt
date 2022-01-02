package cn.kizzzy.qqt;

import cn.kizzzy.image.ImageCreator;
import cn.kizzzy.image.creator.BufferedImageCallback;
import cn.kizzzy.image.creator.QqtImgCreator;

import java.awt.image.BufferedImage;

public class QqtImgHelper {
    
    private static final ImageCreator<QqtImgItem, BufferedImage> creator_1
        = new QqtImgCreator();
    
    public static BufferedImage toImage(QqtImgItem item) {
        return creator_1.Create(item, new BufferedImageCallback());
    }
}

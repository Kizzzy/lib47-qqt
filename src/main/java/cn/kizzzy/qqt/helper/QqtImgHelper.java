package cn.kizzzy.qqt.helper;

import cn.kizzzy.image.ImageCreator;
import cn.kizzzy.image.creator.BufferedImageCallback;
import cn.kizzzy.image.creator.QqtImgCreator;
import cn.kizzzy.qqt.QqtImgItem;

import java.awt.image.BufferedImage;

public class QqtImgHelper {
    
    private static final ImageCreator<QqtImgItem, BufferedImage> creator_1
        = new QqtImgCreator();
    
    public static BufferedImage toImage(QqtImgItem item) {
        return toImage(item, false);
    }
    
    public static BufferedImage toImage(QqtImgItem item, boolean fixed) {
        BufferedImage image = creator_1.Create(item, new BufferedImageCallback());
        if (!fixed) {
            return image;
        }
        
        int maxWidth = item.file.maxWidth;
        int maxHeight = item.file.maxHeight;
        int offsetX = item.offsetX;
        int offsetY = item.offsetY;
        
        BufferedImage fixedImage = new BufferedImage(maxWidth, maxHeight, BufferedImage.TYPE_INT_ARGB);
        fixedImage.getGraphics().drawImage(image, offsetX, offsetY, null);
        return fixedImage;
    }
    
    public static BufferedImage toImageByCustom(QqtImgItem item, int x, int y, int width, int height) {
        float offsetX = -item.file.maxWidth / 2f - item.file.offsetX + item.offsetX;
        float offsetY = -item.file.maxHeight - item.file.offsetY + 20 + item.offsetY;
        offsetX = width / 2f + offsetX + x;
        offsetY = height + offsetY + y;
        
        BufferedImage fixedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        fixedImage.getGraphics().drawImage(toImage(item), (int) offsetX, (int) offsetY, null);
        return fixedImage;
    }
}

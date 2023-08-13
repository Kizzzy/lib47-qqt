package cn.kizzzy.qqt.helper;

import cn.kizzzy.image.ImageCreator;
import cn.kizzzy.image.creator.BufferedImageCallback;
import cn.kizzzy.qqt.ImgFile;
import cn.kizzzy.qqt.image.creator.QqtImgCreator;

import java.awt.image.BufferedImage;

public class QqtImgHelper {
    
    private static final ImageCreator<ImgFile.Frame, BufferedImage> creator_1
        = new QqtImgCreator();
    
    public static BufferedImage toImage(ImgFile.Frame frame) {
        return toImage(frame, false);
    }
    
    public static BufferedImage toImage(ImgFile.Frame frame, boolean fixed) {
        BufferedImage image = creator_1.Create(frame, new BufferedImageCallback());
        if (!fixed) {
            return image;
        }
        
        int maxWidth = frame.file.maxWidth;
        int maxHeight = frame.file.maxHeight;
        int offsetX = frame.file.offsetX + frame.offsetX;
        int offsetY = -frame.file.offsetY + frame.offsetY + 20;
        
        BufferedImage fixedImage = new BufferedImage(maxWidth, maxHeight, BufferedImage.TYPE_INT_ARGB);
        fixedImage.getGraphics().drawImage(image, offsetX, offsetY, null);
        return fixedImage;
    }
    
    public static BufferedImage toImageByCustom(ImgFile.Frame frame, int x, int y, int width, int height) {
        float offsetX = -frame.file.maxWidth / 2f - frame.file.offsetX + frame.offsetX;
        float offsetY = -frame.file.maxHeight - frame.file.offsetY + frame.offsetY + 20;
        offsetX = width / 2f + offsetX + x;
        offsetY = height + offsetY + y;
        
        BufferedImage fixedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        fixedImage.getGraphics().drawImage(toImage(frame), (int) offsetX, (int) offsetY, null);
        return fixedImage;
    }
}

package cn.kizzzy.image.creator;

import cn.kizzzy.image.PixelConverter;
import cn.kizzzy.image.PixelConverterSelector;
import cn.kizzzy.image.selector.QqtPixelConverterSelector;
import cn.kizzzy.io.DataInputStreamEx;
import cn.kizzzy.qqt.QqtImgItem;

import java.awt.image.BufferedImage;
import java.io.InputStream;

public class QqtImgCreator extends ImageCreatorAdapter<QqtImgItem, BufferedImage> {
    
    protected static final PixelConverterSelector DEFAULT_SELECTOR
        = new QqtPixelConverterSelector();
    
    public QqtImgCreator() {
        super(DEFAULT_SELECTOR);
    }
    
    @Override
    protected BufferedImage CreateImpl(QqtImgItem item, Callback<BufferedImage> callback) throws Exception {
        PixelConverter converter = selector.select(item.type);
        if (converter != null) {
            try (InputStream is = item.OpenStream();
                 DataInputStreamEx reader = new DataInputStreamEx(is)) {
                int[] buffer = readPixel(reader, converter, item.width, item.height);
                return callback.invoke(buffer, item.width, item.height);
            }
        }
        return null;
    }
}

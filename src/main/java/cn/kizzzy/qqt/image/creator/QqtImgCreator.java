package cn.kizzzy.qqt.image.creator;

import cn.kizzzy.image.PixelConverter;
import cn.kizzzy.image.PixelConverterSelector;
import cn.kizzzy.image.creator.ImageCreatorAdapter;
import cn.kizzzy.qqt.image.selector.QqtPixelConverterSelector;
import cn.kizzzy.io.IFullyReader;
import cn.kizzzy.qqt.QqtImgItem;

import java.awt.image.BufferedImage;

public class QqtImgCreator extends ImageCreatorAdapter<QqtImgItem, BufferedImage> {
    
    protected static final PixelConverterSelector DEFAULT_SELECTOR
        = new QqtPixelConverterSelector();
    
    public QqtImgCreator() {
        super(DEFAULT_SELECTOR);
    }
    
    @Override
    protected BufferedImage CreateImpl(QqtImgItem item, Callback<BufferedImage> callback) throws Exception {
        PixelConverter converter = selector.select(item.file.major);
        if (converter != null && item.valid) {
            try (IFullyReader reader = item.OpenStream()) {
                int[] buffer = readPixel(reader, converter, item.width, item.height);
                if (item.file.major == 0) {
                    try (IFullyReader alpha_reader = item.OpenStream_Alpha()) {
                        byte[] alphas = new byte[item.size_alpha];
                        alpha_reader.read(alphas);
                        for (int i = 0; i < buffer.length; ++i) {
                            int alpha = (int) (alphas[i] / 32f * 255);
                            buffer[i] = buffer[i] & (alpha << 24 | 0xFFFFFF);
                        }
                    }
                }
                return callback.invoke(buffer, item.width, item.height);
            }
        }
        return null;
    }
}

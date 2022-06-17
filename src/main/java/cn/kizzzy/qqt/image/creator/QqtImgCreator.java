package cn.kizzzy.qqt.image.creator;

import cn.kizzzy.image.PixelConverter;
import cn.kizzzy.image.PixelConverterSelector;
import cn.kizzzy.image.creator.ImageCreatorAdapter;
import cn.kizzzy.io.IFullyReader;
import cn.kizzzy.qqt.ImgFile;
import cn.kizzzy.qqt.image.selector.QqtPixelConverterSelector;

import java.awt.image.BufferedImage;

public class QqtImgCreator extends ImageCreatorAdapter<ImgFile.Frame, BufferedImage> {
    
    protected static final PixelConverterSelector DEFAULT_SELECTOR
        = new QqtPixelConverterSelector();
    
    public QqtImgCreator() {
        super(DEFAULT_SELECTOR);
    }
    
    @Override
    protected BufferedImage CreateImpl(ImgFile.Frame frame, Callback<BufferedImage> callback) throws Exception {
        PixelConverter converter = selector.select(frame.file.major);
        if (converter != null && frame.valid) {
            try (IFullyReader reader = frame.getInput()) {
                int[] buffer = readPixel(reader, converter, frame.width, frame.height);
                if (frame.file.major == 0) {
                    try (IFullyReader alpha_reader = frame.OpenStream_Alpha()) {
                        byte[] alphas = new byte[frame.size_alpha];
                        alpha_reader.read(alphas);
                        for (int i = 0; i < buffer.length; ++i) {
                            int alpha = (int) (alphas[i] / 32f * 255);
                            buffer[i] = buffer[i] & (alpha << 24 | 0xFFFFFF);
                        }
                    }
                }
                return callback.invoke(buffer, frame.width, frame.height);
            }
        }
        return null;
    }
}

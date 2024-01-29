package cn.kizzzy.qqt.image.creator;

import cn.kizzzy.image.PixelConverter;
import cn.kizzzy.image.PixelConverterSelector;
import cn.kizzzy.image.creator.ImageCreatorAdapter;
import cn.kizzzy.io.IFullyReader;
import cn.kizzzy.qqt.ImgFile;
import cn.kizzzy.qqt.image.selector.QqtPixelConverterSelector;

public class QqtImgCreator extends ImageCreatorAdapter<ImgFile.Frame> {
    
    protected static final PixelConverterSelector DEFAULT_SELECTOR
        = new QqtPixelConverterSelector();
    
    public QqtImgCreator() {
        super(DEFAULT_SELECTOR);
    }
    
    @Override
    protected <R> R CreateImpl(ImgFile.Frame frame, Callback<R> callback) throws Exception {
        PixelConverter converter = selector.select(frame.file.major);
        if (converter != null && frame.valid) {
            try (IFullyReader reader = frame.pixels.getInput()) {
                int[] buffer = readPixel(reader, converter, frame.width, frame.height);
                if (frame.file.major == 0) {
                    try (IFullyReader alpha_reader = frame.alphas.getInput()) {
                        byte[] alphas = new byte[(int) alpha_reader.length()];
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
        throw new IllegalArgumentException("Unknown Type: " + frame.file.major);
    }
}

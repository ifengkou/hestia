package cn.ifengkou.hestia.serialize.protostuff;

import cn.ifengkou.hestia.serialize.MessageCodecUtil;
import cn.ifengkou.hestia.serialize.MessageDecoder;

/**
 * Decoder 解码器 实现类 ProtostuffCodecUtil
 *
 * @author shenlongguang<https://github.com/ifengkou>
 * @date 2017/2/22 16:13
 */

public class ProtostuffDecoder extends MessageDecoder {
    public ProtostuffDecoder(MessageCodecUtil util) {
        super(util);
    }
}

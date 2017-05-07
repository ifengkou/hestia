package cn.ifengkou.hestia.serialize.protostuff;

import cn.ifengkou.hestia.serialize.MessageCodecUtil;
import cn.ifengkou.hestia.serialize.MessageEncoder;

/**
 * Encoder 解码器
 *
 * @author shenlongguang<https://github.com/ifengkou>
 * @date 2017/2/22 16:12
 */

public class ProtostuffEncoder extends MessageEncoder {
    public ProtostuffEncoder(MessageCodecUtil util) {
        super(util);
    }
}

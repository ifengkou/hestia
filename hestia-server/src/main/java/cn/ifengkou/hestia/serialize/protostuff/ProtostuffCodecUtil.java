package cn.ifengkou.hestia.serialize.protostuff;

import cn.ifengkou.hestia.common.Kit;
import cn.ifengkou.hestia.model.MessageRequest;
import cn.ifengkou.hestia.serialize.MessageCodecUtil;
import com.google.common.io.Closer;
import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * ProtostuffCodecUtil protostuff codec 实现类
 *
 * @author shenlongguang<https://github.com/ifengkou>
 * @date 2017/2/22 16:14
 */

public class ProtostuffCodecUtil implements MessageCodecUtil {
    private final static Logger LOGGER = LoggerFactory.getLogger(ProtostuffCodecUtil.class);
    private static Closer closer = Closer.create();
    private ProtostuffSerializePool pool = ProtostuffSerializePool.getProtostuffPoolInstance();
    private boolean rpcDirect = false;

    public boolean isRpcDirect() {
        return rpcDirect;
    }

    public void setRpcDirect(boolean rpcDirect) {
        this.rpcDirect = rpcDirect;
    }

    public void encode(final ByteBuf out, final Object message) throws IOException {
        try {
            LOGGER.debug("message out");
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            closer.register(byteArrayOutputStream);
            ProtostuffSerialize protostuffSerialization = pool.borrow();
            protostuffSerialization.serialize(byteArrayOutputStream, message);
            byte[] body = byteArrayOutputStream.toByteArray();
            int dataLength = body.length;
            out.writeInt(dataLength);
            out.writeBytes(body);
            pool.restore(protostuffSerialization);
        } finally {
            closer.close();
        }
    }

    @Override
    public Object decode(byte[] body) throws IOException {
        try {
            LOGGER.debug("message in {}", Kit.toHex(body));
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);
            closer.register(byteArrayInputStream);
            ProtostuffSerialize protostuffSerialization = pool.borrow();
            protostuffSerialization.setRpcDirect(rpcDirect);
            Object object = protostuffSerialization.deserialize(byteArrayInputStream);
            pool.restore(protostuffSerialization);
            return object;
        } catch (Exception e) {
            LOGGER.error("error msg:{}", e.getMessage());
            return null;
        } finally {
            closer.close();
        }
    }

}

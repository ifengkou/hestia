package cn.ifengkou.hestia.serialize.protostuff;

import cn.ifengkou.hestia.serialize.MessageCodecUtil;
import com.google.common.io.Closer;
import io.netty.buffer.ByteBuf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author shenlongguang<https://github.com/ifengkou>
 * @Description ProtostuffCodecUtil protostuff codec 实现类
 * @package cn.ifengkou.hestia.serialize.protostuff
 * @date 2017/2/22 16:14
 */

public class ProtostuffCodecUtil implements MessageCodecUtil {
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
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);
            closer.register(byteArrayInputStream);
            ProtostuffSerialize protostuffSerialization = pool.borrow();
            protostuffSerialization.setRpcDirect(rpcDirect);
            Object object = protostuffSerialization.deserialize(byteArrayInputStream);
            pool.restore(protostuffSerialization);
            return object;
        } finally {
            closer.close();
        }

    }

}

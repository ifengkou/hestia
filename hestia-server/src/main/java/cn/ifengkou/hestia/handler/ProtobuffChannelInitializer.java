package cn.ifengkou.hestia.handler;

import cn.ifengkou.hestia.serialize.protostuff.ProtostuffCodecUtil;
import cn.ifengkou.hestia.serialize.protostuff.ProtostuffDecoder;
import cn.ifengkou.hestia.serialize.protostuff.ProtostuffEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 使用 protostuff 序列化bean 的handler
 *
 * @author shenlongguang<https://github.com/ifengkou>
 * @date 2017/2/22 19:42
 */
@Component
@Qualifier("protobuffChannelInitializer")
public class ProtobuffChannelInitializer extends ChannelInitializer<SocketChannel> {
    final public static int LENGTHFIELD_LENGTH = 4;
    final public static int MAX_FRAME_LENGTH = 10240;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        pipeline.addLast(new LengthFieldBasedFrameDecoder(MAX_FRAME_LENGTH, 0, LENGTHFIELD_LENGTH, 0, LENGTHFIELD_LENGTH));
        pipeline.addLast(new LengthFieldPrepender(LENGTHFIELD_LENGTH));

        ProtostuffCodecUtil util = new ProtostuffCodecUtil();
        util.setRpcDirect(true);
        pipeline.addLast(new ProtostuffDecoder(util));
        pipeline.addLast(new MessageRecvHandler());
        pipeline.addLast(new ProtostuffEncoder(util));
        //空闲读超时设置
        //pipeline.addLast(new IdleStateHandler(40, 0, 0));

    }
}

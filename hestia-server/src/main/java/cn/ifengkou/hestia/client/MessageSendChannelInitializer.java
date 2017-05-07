package cn.ifengkou.hestia.client;

import cn.ifengkou.hestia.serialize.protostuff.ProtostuffCodecUtil;
import cn.ifengkou.hestia.serialize.protostuff.ProtostuffDecoder;
import cn.ifengkou.hestia.serialize.protostuff.ProtostuffEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

/**
 * 描述
 *
 * @author shenlongguang<https://github.com/ifengkou>
 * @date 2017/3/2 16:10
 */
public class MessageSendChannelInitializer extends ChannelInitializer<SocketChannel> {
    final public static int MESSAGE_LENGTH = 4;
    final public static int MAX_FRAME_LENGTH = 10240;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        pipeline.addLast("frameDecode", new LengthFieldBasedFrameDecoder(MAX_FRAME_LENGTH, 0, MESSAGE_LENGTH, 0, MESSAGE_LENGTH));
        pipeline.addLast("frameEncode", new LengthFieldPrepender(MESSAGE_LENGTH));

        ProtostuffCodecUtil util = new ProtostuffCodecUtil();
        util.setRpcDirect(false);
        pipeline.addLast("decoder", new ProtostuffDecoder(util));

        pipeline.addLast("encoder", new ProtostuffEncoder(util));
        pipeline.addLast("handler", new MessageSendHandler());
    }
}

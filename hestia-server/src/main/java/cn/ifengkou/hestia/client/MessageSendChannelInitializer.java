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
        //ObjectDecoder的基类半包解码器LengthFieldBasedFrameDecoder的报文格式保持兼容。因为底层的父类LengthFieldBasedFrameDecoder
        //的初始化参数即为super(maxObjectSize, 0, 4, 0, 4);
        pipeline.addLast(new LengthFieldBasedFrameDecoder(MAX_FRAME_LENGTH, 0, MessageSendChannelInitializer.MESSAGE_LENGTH, 0, MessageSendChannelInitializer.MESSAGE_LENGTH));
        //利用LengthFieldPrepender回填补充ObjectDecoder消息报文头
        pipeline.addLast(new LengthFieldPrepender(MessageSendChannelInitializer.MESSAGE_LENGTH));

        ProtostuffCodecUtil util = new ProtostuffCodecUtil();
        util.setRpcDirect(false);
        pipeline.addLast(new ProtostuffDecoder(util));
        pipeline.addLast(new MessageSendHandler());
        pipeline.addLast(new ProtostuffEncoder(util));


    }
}

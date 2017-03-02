package cn.ifengkou.hestia.handler;

import cn.ifengkou.hestia.serialize.protostuff.ProtostuffCodecUtil;
import cn.ifengkou.hestia.serialize.protostuff.ProtostuffDecoder;
import cn.ifengkou.hestia.serialize.protostuff.ProtostuffEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * 使用 protostuff 序列化bean 的handler
 *
 * @author shenlongguang<https://github.com/ifengkou>
 * @date 2017/2/22 19:42
 */
public class ProtobuffChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        ProtostuffCodecUtil util = new ProtostuffCodecUtil();
        util.setRpcDirect(true);
        pipeline.addLast(new ProtostuffDecoder(util));
        pipeline.addLast(new MessageRecvHandler());
        pipeline.addLast(new ProtostuffEncoder(util));
    }
}

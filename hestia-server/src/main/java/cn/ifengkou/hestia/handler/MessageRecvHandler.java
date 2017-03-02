package cn.ifengkou.hestia.handler;

import cn.ifengkou.hestia.model.MessageRequest;
import cn.ifengkou.hestia.model.MessageResponse;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * MessageRequest处理类；业务处理类
 *
 * @author shenlongguang<https://github.com/ifengkou>
 * @date 2017/2/22 18:02
 */

public class MessageRecvHandler extends ChannelInboundHandlerAdapter {
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MessageRequest request = (MessageRequest) msg;
        MessageResponse response = new MessageResponse();
        //TODO 消息处理类
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }
}

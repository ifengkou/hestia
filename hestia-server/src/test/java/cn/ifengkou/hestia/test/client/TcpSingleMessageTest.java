package cn.ifengkou.hestia.test.client;

import cn.ifengkou.hestia.client.MessageSendChannelInitializer;
import cn.ifengkou.hestia.model.MessageRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.UUID;

/**
 * 描述
 *
 * @author shenlongguang<https://github.com/ifengkou>
 * @date 2017/3/3 16:47
 */

public class TcpSingleMessageTest {
    static String HOST = "127.0.0.1";
    static int PORT = 8090;
    static Channel channel;

    public static final Bootstrap initBootstrap() {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group).channel(NioSocketChannel.class);
        b.handler(new MessageSendChannelInitializer());
        b.option(ChannelOption.SO_KEEPALIVE, true);
        return b;
    }

    public static void main(String[] args) throws Exception {

        try {
            Bootstrap bootstrap = initBootstrap();
            channel = bootstrap.connect(HOST, PORT).sync().channel();
        } catch (Exception e) {
            System.out.printf(String.format("连接Server(IP[%s],PORT[%s])失败", HOST, PORT), e);
            return;
        }

        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setMessageType(0);
        messageRequest.setMessageId(UUID.randomUUID().toString());
        messageRequest.setMessage("message_No.001 从client 001 发出");

        channel.writeAndFlush(messageRequest).addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                System.out.println("--消息发送");
            }
        });

    }
}

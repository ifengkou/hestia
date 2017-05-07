package cn.ifengkou.hestia.test.client;

import cn.ifengkou.hestia.client.MessageSendChannelInitializer;
import cn.ifengkou.hestia.model.MessageRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.commons.lang3.time.StopWatch;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class TcpParallelTest {
    static String HOST = "127.0.0.1";
    static int PORT = 8090;
    static Channel channel;

    public static void main(String[] args) throws Exception {

        try {
            Bootstrap bootstrap = initBootstrap();
            channel = bootstrap.connect(HOST, PORT).sync().channel();
        } catch (Exception e) {
            System.out.printf(String.format("连接Server(IP[%s],PORT[%s])失败", HOST, PORT), e);
            return;
        }

        //并行度1000
        int parallel = 1000;

        for (int i = 0; i < 1; i++) {
            addTask(parallel);
            System.out.printf("[author sloong] Netty Server 消息协议序列化第[%d]轮并发验证结束!\n\n", i);
        }

        //channel.close();
    }

    public static void addTask(int parallel) throws InterruptedException {
        TcpParallelTest.parallelSendSuccessMessageTask(parallel);
        TimeUnit.MILLISECONDS.sleep(30);
    }

    public static void parallelSendSuccessMessageTask(int parallel) throws InterruptedException {
        //开始计时
        StopWatch sw = new StopWatch();
        sw.start();

        CountDownLatch signal = new CountDownLatch(1);
        CountDownLatch finish = new CountDownLatch(parallel);

        for (int index = 0; index < parallel; index++) {
            MessageRequest messageRequest = new MessageRequest();
            messageRequest.setMessageType(0);
            messageRequest.setMessageId(UUID.randomUUID().toString());
            messageRequest.setMessage("message_No." + index);
            SendMessageParallelRequestThread client = new SendMessageParallelRequestThread(channel, messageRequest, signal, finish, index);
            new Thread(client).start();
        }

        signal.countDown();
        finish.await();
        sw.stop();

        String tip = String.format("并发发送消息调用总共耗时: [%s] 毫秒", sw.getTime());
        System.out.println(tip);
    }

    public static final Bootstrap initBootstrap() {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group).channel(NioSocketChannel.class);
        b.handler(new MessageSendChannelInitializer());
        b.option(ChannelOption.SO_KEEPALIVE, true);
        return b;
    }
}

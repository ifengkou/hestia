package cn.ifengkou.hestia.test.client;

import cn.ifengkou.hestia.model.MessageRequest;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

public class SendMessageParallelRequestThread implements Runnable {
    private final static Logger LOGGER = LoggerFactory.getLogger(SendMessageParallelRequestThread.class);
    private CountDownLatch signal;
    private CountDownLatch finish;
    private int taskNumber = 0;
    private MessageRequest messageRequest;
    private Channel channel;

    public SendMessageParallelRequestThread(Channel channel, MessageRequest message, CountDownLatch signal, CountDownLatch finish, int taskNumber) {
        this.signal = signal;
        this.finish = finish;
        this.taskNumber = taskNumber;
        this.messageRequest = message;
        this.channel = channel;
    }

    public void run() {
        try {
            signal.await();
            LOGGER.info("sendMessage {}", taskNumber);
            channel.writeAndFlush(messageRequest);
            finish.countDown();
        } catch (InterruptedException ex) {
            LOGGER.error("Client SendMessage failed! taskNumber{},exception:{}", taskNumber, ex.getMessage());
        }
    }
}


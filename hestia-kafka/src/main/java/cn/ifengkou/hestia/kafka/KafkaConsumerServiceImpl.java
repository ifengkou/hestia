package cn.ifengkou.hestia.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.config.ContainerProperties;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author shenlongguang<https://github.com/ifengkou>
 * @date 2017/3/29
 */
public class KafkaConsumerServiceImpl implements IKafkaConsumerService,InitializingBean{
    @Autowired
    private KafkaMessageListenerContainer kafkaMessageListenerContainer;
    private final static Logger LOG = LoggerFactory.getLogger(KafkaConsumerServiceImpl.class);
    private int threadNum = 8;
    private int maxQueueSize = 2000;
    private ExecutorService executorService = new ThreadPoolExecutor(threadNum,
        threadNum, 0L, TimeUnit.MILLISECONDS,
        new ArrayBlockingQueue<Runnable>(maxQueueSize),
        new ThreadPoolExecutor.CallerRunsPolicy());

    @Override
    public void onMessage(final ConsumerRecord<Integer, String> record) {
        LOG.info("===============processMessage===============");
        LOG.info("the kafka message is arriving with topic = {}, partition = {}, key = {}, value = {}",
            new Object[]{record.topic(), record.partition(), record.key(), record.value()});
        //这里收到消息后，开启了一个线程来处理
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    String msg = record.value();
                    LOG.info(msg);
                }
            });
    }

    @Override//设置监听
    public void afterPropertiesSet() throws Exception {
        ContainerProperties containerProperties = kafkaMessageListenerContainer.getContainerProperties();
        if (null != containerProperties) {
            containerProperties.setMessageListener(this);
        }
    }
}

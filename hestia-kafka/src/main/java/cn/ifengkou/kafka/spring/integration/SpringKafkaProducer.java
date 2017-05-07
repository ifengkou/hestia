package cn.ifengkou.kafka.spring.integration;

import kafka.javaapi.producer.Producer;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;


/**
 * 描述
 *
 * @author shenlongguang<https://github.com/ifengkou>
 * @date 2017/4/7 8:57
 */

public class SpringKafkaProducer {
    private static final String CONFIG = "/context_adapt.xml";
    private static ClassPathXmlApplicationContext ctx;

    static {
        //ClassPathXmlApplicationContext实际上就已经包含了BeanFactory所提供的功能

        ctx = new ClassPathXmlApplicationContext(CONFIG, Producer.class);
        ctx.start();
    }

    public static void sendMessage2Kafka(String message){
        final MessageChannel channel = ctx.getBean("inputToKafka", MessageChannel.class);
        channel.send(MessageBuilder.withPayload(message).build());
    }
}

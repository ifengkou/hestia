package cn.ifengkou.kafka.javaapi.test;

/**
 * 描述
 *
 * @author shenlongguang<https://github.com/ifengkou>
 * @date 2017/4/6 17:24
 */

public class TestKafkaJavaApi {

    public static void main(String[] args) {
        KafkaProducer producerThread = new KafkaProducer(KafkaProperties.topic);
        producerThread.start();
        KafkaConsumer consumerThread = new KafkaConsumer(KafkaProperties.topic);
        consumerThread.start();
    }
}

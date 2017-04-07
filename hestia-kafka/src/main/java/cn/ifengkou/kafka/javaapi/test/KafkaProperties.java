package cn.ifengkou.kafka.javaapi.test;

/**
 * 描述
 *
 * @author shenlongguang<https://github.com/ifengkou>
 * @date 2017/4/6 17:15
 */

public interface KafkaProperties {
    final static String zkConnect = "192.168.56.129:2181";
    final static String groupId = "group1";
    final static String topic = "my-replicated-topic2";
    final static String kafkaServerURL = "192.168.56.131";
    final static int kafkaServerPort = 9092;
    final static int kafkaProducerBufferSize = 64 * 1024;
    final static int connectionTimeOut = 20000;
    final static int reconnectInterval = 10000;
    final static String topic2 = "my-replicated-topic";
    final static String topic3 = "test";
    final static String clientId = "SimpleConsumerDemoClient";
}

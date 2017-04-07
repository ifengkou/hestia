package cn.ifengkou.kafka.spring.integration;

/**
 * 描述
 *
 * @author shenlongguang<https://github.com/ifengkou>
 * @date 2017/4/7 9:00
 */

public class TestSpringKafkaProducer {
    public static void main(String[] args) {

        SpringKafkaProducer.sendMessage2Kafka("springKafka");

    }
}

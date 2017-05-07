package cn.ifengkou.hestia.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;

/**
 * @author shenlongguang<https://github.com/ifengkou>
 * @date 2017/3/29
 */
public interface IKafkaConsumerService {
    void onMessage(ConsumerRecord<Integer, String> record);
    void afterPropertiesSet() throws Exception;
}

package cn.ifengkou.hestia.kafka;

/**
 * @author shenlongguang<https://github.com/ifengkou>
 * @date 2017/3/29
 */
public interface IKafkaProducerService {
    void sendMessage(String topic, String data);
    void sendMessage(String topic, int key, String data);
}

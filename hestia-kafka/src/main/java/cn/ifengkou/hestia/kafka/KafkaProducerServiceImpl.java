package cn.ifengkou.hestia.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * @author shenlongguang<https://github.com/ifengkou>
 * @date 2017/3/29
 */
public class KafkaProducerServiceImpl implements IKafkaProducerService {
    private final static Logger LOG = LoggerFactory.getLogger(KafkaProducerServiceImpl.class);
    @Autowired
    private KafkaTemplate<Integer, String> kafkaTemplate;//这个已经在上述xml文件中配置

    @Override
    public void sendMessage(String topic, String data) {
        LOG.info("the message is to be send by kafka is : topic = {}, data = {}", topic, data);
        kafkaTemplate.setDefaultTopic(topic);
        kafkaTemplate.sendDefault(data);
    }

    @Override
    public void sendMessage(String topic, int key, String data) {
        LOG.info("the message is to be send by kafka is : topic = {}, data = {}", topic, data);
        kafkaTemplate.setDefaultTopic(topic);
        kafkaTemplate.sendDefault(key, data);
    }
}

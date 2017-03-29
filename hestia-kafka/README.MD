java 代码：
http://blog.csdn.net/u011734144/article/details/52351289

##kafka producer 优化

原文：[producer-performance-tuning-for-apache-kafka](http://www.slideshare.net/JiangjieQin/producer-performance-tuning-for-apache-kafka-63147600?qid=84026ff8-243f-49a7-a4d0-69976cf317b7&v=&b=&from_search=9)

译文：http://www.kaimingwan.com/post/kafka/kafka-producerxing-neng-diao-you

##设计疑问点：

### 1. 是否有必要对 producer 进行池化（ObjectPool?）

在使用spring 集成 kafka 时，kafka producer bean 是 单例的。 如果我创建 multiple producers ,对性能是否有提升?

回答一：https://github.com/spring-projects/spring-kafka/issues/92

From [the producer javadoc ...](https://kafka.apache.org/090/javadoc/index.html?org/apache/kafka/clients/producer/KafkaProducer.html)

`The producer is thread safe and sharing a single producer instance across threads will generally be faster than having multiple instances.`

... so spring-kafka uses that model in DefaultKafkaProducerFactory - it always returns the same producer. If you want to use multiple connections, you will need separate factories.

回答二：

KafkaProducer can send message asynchronously, so you didn't need to start multiple KafkaProducer to have performance.
This is not the same pattern for the consumer.

回答三：

kafka设计逻辑：并没有使用持久性质的connection，so having a pool of connections is just a memory allocation issue

回复四：

In general, a single producer for all topics will be more network efficient.


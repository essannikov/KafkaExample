package consumer_third;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Properties;
import java.util.stream.Collectors;

import consumer_third.service.OrderEvent;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

public class ConsumerApplicationThird {
    private static final Logger LOG = LoggerFactory.getLogger(ConsumerApplicationThird.class);

    private static String topic = "send-order-event";

    @SuppressWarnings("boxing")
    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("consumer_third/src/main/resources/app.properties"));
//        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap_servers);
//        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
//        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, offsetReset);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        properties.put(JsonDeserializer.VALUE_DEFAULT_TYPE, OrderEvent.class);

        try (KafkaConsumer<String, OrderEvent> consumer = new KafkaConsumer<>(properties)) {
            consumer.subscribe(Collections.singleton(topic), new ConsumerRebalanceListener() {
                @Override
                public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
                    LOG.info("onPartitionsRevoked - partitions:{}", formatPartitions(partitions));
                }

                @Override
                public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
                    LOG.info("onPartitionsAssigned - partitions: {}", formatPartitions(partitions));
                }
            });
            while (true) {
                ConsumerRecords<String, OrderEvent> records = consumer.poll(Duration.ofSeconds(2));
                for (ConsumerRecord<String, OrderEvent> data : records) {
                    LOG.info("key = {}, value = {} => partition = {}, offset= {}", data.key(), data.value(), data.partition(), data.offset());
                }
            }
        } catch (Exception e) {
            LOG.error("Something goes wrong: {}", e.getMessage(), e);
        }
    }
    @SuppressWarnings("boxing")
    public static String formatPartitions(Collection<TopicPartition> partitions) {
        return partitions.stream()
                .map(topicPartition -> String.format("topic: %s, partition: %s", topicPartition.topic(), topicPartition.partition()))
                .collect(Collectors.joining(", ", "[", "]"));
    }
}
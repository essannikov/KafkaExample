package consumer_first.service.messaging.service;

import consumer_first.service.OrderService;
import consumer_first.service.dto.OrderDto;
import consumer_first.service.messaging.event.OrderEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class KafkaMessagingService {
    private static final String topicCreateOrder = "${topic.send-order}";
    private static final String kafkaConsumerGroupId = "${spring.kafka.consumer.group-id}";
    private final OrderService orderService;
    private final ModelMapper modelMapper;

    @Transactional
    @KafkaListener(topics = topicCreateOrder,
            groupId = kafkaConsumerGroupId,
            properties = {"spring.json.value.default.type=consumer_first.service.messaging.event.OrderEvent"})
    public OrderEvent createOrder(OrderEvent orderEvent) {
        log.info("Message consumed {}", orderEvent);
        orderService.save(modelMapper.map(orderEvent, OrderDto.class));
        return orderEvent;
    }
}
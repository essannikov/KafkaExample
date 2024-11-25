package producer.service.messaging.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import producer.model.Order;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import producer.service.messaging.event.OrderSendEvent;
import producer.service.messaging.service.KafkaMessagingService;


@Slf4j
@Component
@RequiredArgsConstructor
public class Producer {
    private final KafkaMessagingService kafkaMessagingService;
    private final ModelMapper modelMapper;

    public Order sendOrderEvent(Order order) {
        kafkaMessagingService.sendOrder(modelMapper.map(order, OrderSendEvent.class));
        log.info("Send order from producer {}", order);
        return order;
    }
}
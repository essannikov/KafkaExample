package producer.service.messaging.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import producer.service.messaging.event.OrderSendEvent;

@Service
@RequiredArgsConstructor
public class KafkaMessagingService {
    @Value("${topic.send-order}")
    private String sendClientTopic;

    //Иньекция спринг бина без @Autowired
    //Достаточно объявить переменную инъектируемого бина как pivate final
    //и использовать аннотацию Lombok @RequiredArgsConstructor.
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendOrder(OrderSendEvent orderSendEvent) {
        kafkaTemplate.send(sendClientTopic, orderSendEvent.getBarCode(), orderSendEvent);
    }
}
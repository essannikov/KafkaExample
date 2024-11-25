package consumer_first.service.messaging.service.util;

import consumer_first.domain.Order;
import consumer_first.domain.Status;
import consumer_first.service.messaging.event.OrderEvent;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FakeOrder {

    public static OrderEvent getOrderEvent(){
        return new OrderEvent(
                "pensil",
                "0000003",
                100,
                new BigDecimal(0.99)
        );
    }

    public static Order getOrder(){
        return new Order(
                1L,
                "pensil",
                "0000003",
                100,
                new BigDecimal(0.99),
                new BigDecimal(99.0),
                LocalDateTime.of(2024,11,9,15,56,00),
                Status.APPROVED
        );
    }
}
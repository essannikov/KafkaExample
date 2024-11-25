package consumer_first.service;

import consumer_first.domain.Order;
import consumer_first.service.dto.OrderDto;

public interface OrderService {
    Order save(OrderDto clientDto);
}
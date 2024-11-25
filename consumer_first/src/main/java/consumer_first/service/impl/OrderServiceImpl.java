package consumer_first.service.impl;

import consumer_first.domain.Order;
import consumer_first.domain.Status;
import consumer_first.domain.repository.OrdersRepository;
import consumer_first.service.dto.OrderDto;
import consumer_first.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {
    private final OrdersRepository ordersRepository;

    @Override
    @Transactional
    public Order save(OrderDto clientDto) {
        Order order = Order.builder()
                .productName(clientDto.getProductName())
                .barCode(clientDto.getBarCode())
                .quantity(clientDto.getQuantity())
                .price(clientDto.getPrice())
                .amount(new BigDecimal(clientDto.getQuantity()).multiply(clientDto.getPrice()))
                .orderDate(LocalDateTime.now())
                .status(Status.APPROVED)
                .build();

        ordersRepository.save(order);

        log.info("Save order");

        return order;
    }
}
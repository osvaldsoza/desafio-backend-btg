package com.github.osvaldsoza.listener;

import com.github.osvaldsoza.config.RabbitMqConfig;
import com.github.osvaldsoza.listener.dto.OrderCreatedEventDTO;
import com.github.osvaldsoza.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedListener {

    private final Logger logger = LoggerFactory.getLogger(OrderCreatedListener.class);

    private final OrderService orderService;

    public OrderCreatedListener(OrderService orderService) {
        this.orderService = orderService;
    }

    @RabbitListener(queues = RabbitMqConfig.ORDER_CREATED_QUEUE)
    public void listen(Message<OrderCreatedEventDTO> message) {
        logger.info("Messafe consumed: {}", message);

        orderService.save(message.getPayload());
    }
}

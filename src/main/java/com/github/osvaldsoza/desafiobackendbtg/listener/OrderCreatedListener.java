package com.github.osvaldsoza.desafiobackendbtg.listener;

import com.github.osvaldsoza.desafiobackendbtg.listener.dto.OrderCreatedEventDTO;
import com.github.osvaldsoza.desafiobackendbtg.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import static com.github.osvaldsoza.desafiobackendbtg.config.RabbitMqConfig.ORDER_CREATED_QUEUE;

@Component
public class OrderCreatedListener {

    private final Logger logger = LoggerFactory.getLogger(OrderCreatedListener.class);

    private final OrderService orderService;

    public OrderCreatedListener(OrderService orderService) {
        this.orderService = orderService;
    }

    @RabbitListener(queues = ORDER_CREATED_QUEUE)
    public void listen(Message<OrderCreatedEventDTO> message) {
        logger.info("Messafe consumed: {}", message);

        orderService.save(message.getPayload());
    }
}

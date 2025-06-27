package com.github.osvaldsoza.controller;

import com.github.osvaldsoza.config.RabbitMqConfig;
import com.github.osvaldsoza.controller.dto.ApiResponse;
import com.github.osvaldsoza.controller.dto.OrderResponse;
import com.github.osvaldsoza.controller.dto.PaginationResponse;
import com.github.osvaldsoza.controller.util.ApiAcceptedResponse;
import com.github.osvaldsoza.listener.dto.OrderCreatedEventDTO;
import com.github.osvaldsoza.service.OrderService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
public class OrderController {

    private final OrderService orderService;

   private final RabbitTemplate rabbitTemplate;

    public OrderController(OrderService orderService, RabbitTemplate rabbitTemplate) {
        this.orderService = orderService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/orders")
    public ResponseEntity<?> createOrder(@RequestBody OrderCreatedEventDTO orderCreatedEventDTO){
        rabbitTemplate.convertAndSend("", RabbitMqConfig.ORDER_CREATED_QUEUE, orderCreatedEventDTO);

        ApiAcceptedResponse response = ApiAcceptedResponse.of(
                "Order received and will be processed soon.",
                UUID.randomUUID().toString()
        );

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(response);
    }

    @GetMapping("/customers/{customerId}/orders")
    public ResponseEntity<ApiResponse<OrderResponse>> listOrders(
            @PathVariable("customerId") Long customerId,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {

        var pageResponse = orderService.findAllByCustomerId(customerId, PageRequest.of(page, pageSize));
        var totalOnOrders = orderService.findTotalOnOrdersByCustomerId(customerId);

        return ResponseEntity.ok().body(new ApiResponse<>(
                Map.of("totalOnOrders", totalOnOrders),
                pageResponse.getContent(),
                PaginationResponse.fromPage(pageResponse)
        ));
    }
}

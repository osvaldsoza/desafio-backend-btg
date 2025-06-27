package com.github.osvaldsoza.service;

import com.github.osvaldsoza.controller.dto.OrderResponse;
import com.github.osvaldsoza.entity.OrderEntity;
import com.github.osvaldsoza.entity.OrderItem;
import com.github.osvaldsoza.listener.dto.OrderCreatedEventDTO;
import com.github.osvaldsoza.listener.dto.OrderItemEventDTO;
import com.github.osvaldsoza.repository.OrderRepository;
import org.bson.Document;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final MongoTemplate mongoTemplate;

    private final ModelMapper modelMapper;

    public OrderService(OrderRepository orderRepository, MongoTemplate mongoTemplate, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.mongoTemplate = mongoTemplate;
        this.modelMapper = modelMapper;
    }

    public Page<OrderResponse> findAllByCustomerId(Long customerId, PageRequest pageRequest) {
        var orders = orderRepository.findAllByCustomerId(customerId, pageRequest);
        return orders.map(order -> modelMapper.map(order, OrderResponse.class));
    }

    public BigDecimal findTotalOnOrdersByCustomerId(Long customerId){
        var aggregations = newAggregation(
                match(Criteria.where("customerId").is(customerId)),
                group().sum("total").as("total")
        );

        var response = mongoTemplate.aggregate(aggregations, "tb_orders", Document.class);

        return new BigDecimal(response.getUniqueMappedResult().get("total").toString());
    }

    public void save(OrderCreatedEventDTO orderCreatedEventDTO) {
        var entity = modelMapper.map(orderCreatedEventDTO, OrderEntity.class);
        entity.setItems(mapOrderItems(orderCreatedEventDTO.getItens()));
        entity.setTotal(getTotal(orderCreatedEventDTO));

        orderRepository.save(entity);
    }

    private List<OrderItem> mapOrderItems(List<OrderItemEventDTO> orderItemEventDTOS) {
        return orderItemEventDTOS.stream()
                .map(orderItemEventDTO -> modelMapper.map(orderItemEventDTO, OrderItem.class))
                .toList();
    }

    private BigDecimal getTotal(OrderCreatedEventDTO event) {
        return event.getItens()
                .stream().map(item -> item.getPreco().multiply(BigDecimal.valueOf(item.getQuantidade())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }
}

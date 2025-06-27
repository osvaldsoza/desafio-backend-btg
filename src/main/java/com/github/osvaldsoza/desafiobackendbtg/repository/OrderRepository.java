package com.github.osvaldsoza.desafiobackendbtg.repository;

import com.github.osvaldsoza.desafiobackendbtg.controller.dto.OrderResponse;
import com.github.osvaldsoza.desafiobackendbtg.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<OrderEntity,Long> {

    Page<OrderResponse> findAllByCustomerId(Long customerId, PageRequest pageRequest);
}

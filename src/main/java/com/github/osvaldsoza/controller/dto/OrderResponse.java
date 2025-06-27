package com.github.osvaldsoza.controller.dto;

import java.math.BigDecimal;

public record OrderResponse(Long orderId, Long customerId, BigDecimal total) {
}

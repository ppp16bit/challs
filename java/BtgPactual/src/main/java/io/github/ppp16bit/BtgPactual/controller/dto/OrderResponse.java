package io.github.ppp16bit.BtgPactual.controller.dto;

import io.github.ppp16bit.BtgPactual.entity.OrderEntity;

import java.math.BigDecimal;

public record OrderResponse(Long orderID, Long customerID, BigDecimal total) {
    public static OrderResponse fromEntity(OrderEntity entity) {
        return new OrderResponse(entity.getOrderID(), entity.getCustomerID(), entity.getTotal());
    }
}
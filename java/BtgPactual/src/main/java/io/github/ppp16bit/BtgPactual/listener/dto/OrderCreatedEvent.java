package io.github.ppp16bit.BtgPactual.listener.dto;

import java.util.List;

public record OrderCreatedEvent(Long OrderCode, Long ClientCode, List<OrderItemEvent> items) {}
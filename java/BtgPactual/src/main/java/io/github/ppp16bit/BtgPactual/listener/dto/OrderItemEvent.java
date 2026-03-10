package io.github.ppp16bit.BtgPactual.listener.dto;

import java.math.BigDecimal;

public record OrderItemEvent(String product, Integer quantity, BigDecimal price) {}
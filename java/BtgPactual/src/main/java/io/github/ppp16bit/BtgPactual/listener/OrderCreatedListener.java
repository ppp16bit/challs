package io.github.ppp16bit.BtgPactual.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import io.github.ppp16bit.BtgPactual.listener.dto.OrderCreatedEvent;
import io.github.ppp16bit.BtgPactual.service.OrderService;

import static io.github.ppp16bit.BtgPactual.config.RabbitMqConfig.ORDER_CREATED_QUEUE;

@Component
public class OrderCreatedListener {
    private final Logger logger = LoggerFactory.getLogger(OrderCreatedListener.class);
    private final OrderService orderService;

    public OrderCreatedListener(OrderService orderService) {
        this.orderService = orderService;
    }

    @RabbitListener(queues = ORDER_CREATED_QUEUE)
    public void listen(Message<OrderCreatedEvent> message) {
        logger.info("messaged consumed: {}", message);
        orderService.save(message.getPayload());
    }
}
package io.github.ppp16bit.BtgPactual.service;

import java.math.BigDecimal;
import java.util.List;

import org.bson.Document;
import org.bson.types.Decimal128;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

import io.github.ppp16bit.BtgPactual.controller.dto.OrderResponse;
import io.github.ppp16bit.BtgPactual.entity.OrderEntity;
import io.github.ppp16bit.BtgPactual.entity.OrderItem;
import io.github.ppp16bit.BtgPactual.listener.dto.OrderCreatedEvent;
import io.github.ppp16bit.BtgPactual.repository.OrderRepository;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final MongoTemplate mongoTemplate;

    public OrderService(OrderRepository orderRepository, MongoTemplate mongoTemplate) {
        this.orderRepository = orderRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public Page<OrderResponse> findAllByCustomerID(Long customerID, PageRequest pageRequest) {
        var orders = orderRepository.findAllByCustomerID(customerID, pageRequest);
        return orders.map(OrderResponse::fromEntity);
    }

    public BigDecimal findTotalOnOrdersByCustomerID(Long customerID) {
        var aggregations = newAggregation(
            match(Criteria.where("customerID").is(customerID)),
            group().sum("total").as("total")
        ); 

        var response = mongoTemplate.aggregate(aggregations, "table_orders", Document.class);
        var result = response.getUniqueMappedResult();

        if (result == null || result.get("total") == null) {
            return BigDecimal.ZERO;
        }
        return ((Decimal128) result.get("total")).bigDecimalValue();
    }

    private BigDecimal getTotal(OrderCreatedEvent event) {
        return event.items()
                .stream()
                .map(i -> i.price().multiply(BigDecimal.valueOf(i.quantity())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    private static List<OrderItem> getOrderItems(OrderCreatedEvent event) {
        return event.items().stream()
                .map(i -> new OrderItem(i.product(), i.quantity(), i.price()))
                .toList();
    }

    public void save(OrderCreatedEvent event) {
        var entity = new OrderEntity();
        entity.setOrderID(event.OrderCode());
        entity.setCustomerID(event.ClientCode());
        entity.setItems(getOrderItems(event));
        entity.setTotal(getTotal(event));
        orderRepository.save(entity);
    }
}
package io.github.ppp16bit.BtgPactual.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import io.github.ppp16bit.BtgPactual.entity.OrderEntity;

public interface OrderRepository extends MongoRepository<OrderEntity, Long> {
    Page<OrderEntity> findAllByCustomerID(Long customerID, PageRequest pageRequest);
}

package br.com.postech.techchallange_order.infrastructure.adapters.out.persistence;

import br.com.postech.techchallange_order.domain.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMongoRepository extends MongoRepository<Order, String> {
}
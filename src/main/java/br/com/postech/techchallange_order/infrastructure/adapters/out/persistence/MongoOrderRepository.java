package br.com.postech.techchallange_order.infrastructure.adapters.out.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.postech.techchallange_order.domain.model.Order;

@Repository
public interface MongoOrderRepository extends MongoRepository<Order, String> {
	// Spring Data provides save implementations; do not expose OrderRepositoryPort
	// here
}
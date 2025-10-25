package br.com.postech.techchallange_order.infrastructure.adapters.out.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.postech.techchallange_order.domain.model.Order;
import br.com.postech.techchallange_order.domain.ports.out.OrderRepositoryPort;

@Repository
public interface MongoOrderRepository extends MongoRepository<Order, String>, OrderRepositoryPort {
	@Override
	default Order save(Order order) {
		return this.save(order);
	}
}
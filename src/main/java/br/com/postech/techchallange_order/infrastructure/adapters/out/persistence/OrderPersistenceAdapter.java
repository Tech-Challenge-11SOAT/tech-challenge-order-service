package br.com.postech.techchallange_order.infrastructure.adapters.out.persistence;

import br.com.postech.techchallange_order.domain.model.Order;
import br.com.postech.techchallange_order.domain.ports.out.OrderRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class OrderPersistenceAdapter implements OrderRepository {

	private final OrderMongoRepository orderMongoRepository;

	public OrderPersistenceAdapter(OrderMongoRepository orderMongoRepository) {
		this.orderMongoRepository = orderMongoRepository;
	}

	@Override
	public Order save(Order order) {
		return orderMongoRepository.save(order);
	}

	@Override
	public Optional<Order> findById(String orderId) {
		return orderMongoRepository.findById(orderId);
	}

	@Override
	public List<Order> findAll() {
		return orderMongoRepository.findAll();
	}

	@Override
	public void deleteById(String orderId) {
		orderMongoRepository.deleteById(orderId);
	}
}
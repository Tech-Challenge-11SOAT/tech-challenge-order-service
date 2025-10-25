package br.com.postech.techchallange_order.infrastructure.adapters.out.persistence;

import org.springframework.stereotype.Component;

import br.com.postech.techchallange_order.domain.model.Order;
import br.com.postech.techchallange_order.domain.ports.out.OrderRepositoryPort;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MongoOrderAdapter implements OrderRepositoryPort {

	private final MongoOrderRepository repository;

	@Override
	public Order save(Order order) {
		return repository.save(order);
	}
}
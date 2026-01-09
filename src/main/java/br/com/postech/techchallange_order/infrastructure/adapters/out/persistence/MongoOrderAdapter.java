package br.com.postech.techchallange_order.infrastructure.adapters.out.persistence;

import org.springframework.stereotype.Component;

import br.com.postech.techchallange_order.domain.model.Order;
import br.com.postech.techchallange_order.domain.ports.out.OrderRepositoryPort;
import br.com.postech.techchallange_order.infrastructure.adapters.out.mongodb.mapper.OrderMapper;
import br.com.postech.techchallange_order.infrastructure.adapters.out.mongodb.model.OrderDocument;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MongoOrderAdapter implements OrderRepositoryPort {

	private final MongoOrderRepository repository;

	@Override
	public Order save(Order order) {
		OrderDocument document = OrderMapper.toDocument(order);

		OrderDocument savedDocument = repository.save(document);
		return OrderMapper.toDomain(savedDocument);
	}
}
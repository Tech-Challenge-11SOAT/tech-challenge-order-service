package br.com.postech.techchallange_order.infrastructure.adapters.out.persistence;

import org.springframework.stereotype.Component;

import br.com.postech.techchallange_order.domain.model.OrderQueueItem;
import br.com.postech.techchallange_order.domain.ports.out.QueueRepositoryPort;
import br.com.postech.techchallange_order.infrastructure.adapters.out.mongodb.mapper.OrderQueueMapper;
import br.com.postech.techchallange_order.infrastructure.adapters.out.mongodb.model.OrderQueueDocument;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MongoQueueAdapter implements QueueRepositoryPort {

	private final OrderQueueMongoRepository repository;
	private final OrderQueueMapper mapper;

	@Override
	public OrderQueueItem save(OrderQueueItem item) {
		OrderQueueDocument doc = mapper.toDocument(item);
		OrderQueueDocument saved = repository.save(doc);
		return mapper.toDomain(saved);
	}
}

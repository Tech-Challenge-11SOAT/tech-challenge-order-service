package br.com.postech.techchallange_order.application.services;

import org.springframework.stereotype.Service;

import br.com.postech.techchallange_order.domain.model.Order;
import br.com.postech.techchallange_order.domain.ports.in.OrderStatusHistoryUseCase;
import br.com.postech.techchallange_order.infrastructure.adapters.out.mongodb.mapper.OrderHistoryMapper;
import br.com.postech.techchallange_order.infrastructure.adapters.out.mongodb.model.OrderHistoryDocument;
import br.com.postech.techchallange_order.infrastructure.adapters.out.persistence.OrderHistoryMongoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderStatusHistoryService implements OrderStatusHistoryUseCase {

	private final OrderHistoryMongoRepository orderHistoryRepository;

	@Override
	public void recordStatusChange(Order domain, Order.Status previousStatus) {
		OrderHistoryDocument history = OrderHistoryMapper.toDocument(domain, previousStatus);

		this.orderHistoryRepository.save(history);
	}

}

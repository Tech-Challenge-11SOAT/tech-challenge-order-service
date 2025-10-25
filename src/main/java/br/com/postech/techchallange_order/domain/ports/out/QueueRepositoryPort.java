package br.com.postech.techchallange_order.domain.ports.out;

import br.com.postech.techchallange_order.domain.model.OrderQueueItem;

public interface QueueRepositoryPort {
	OrderQueueItem save(OrderQueueItem item);
}

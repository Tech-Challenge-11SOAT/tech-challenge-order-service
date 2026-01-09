package br.com.postech.techchallange_order.domain.ports.in;

import br.com.postech.techchallange_order.domain.model.Order;

public interface OrderStatusHistoryUseCase {
	void recordStatusChange(Order domain, Order.Status previousStatus);
}

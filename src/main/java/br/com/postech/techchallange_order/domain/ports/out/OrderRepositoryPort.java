package br.com.postech.techchallange_order.domain.ports.out;

import br.com.postech.techchallange_order.domain.model.Order;

public interface OrderRepositoryPort {
	Order save(Order order);
}
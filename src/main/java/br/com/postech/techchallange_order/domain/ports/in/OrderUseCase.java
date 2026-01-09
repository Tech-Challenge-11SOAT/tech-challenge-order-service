package br.com.postech.techchallange_order.domain.ports.in;

import br.com.postech.techchallange_order.domain.model.Order;
import br.com.postech.techchallange_order.infrastructure.adapters.in.rest.dto.CheckoutRequest;

public interface OrderUseCase {
	public Order createOrder(CheckoutRequest request);
}

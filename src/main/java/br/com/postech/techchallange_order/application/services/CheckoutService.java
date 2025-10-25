package br.com.postech.techchallange_order.application.services;

import org.springframework.stereotype.Service;

import br.com.postech.techchallange_order.domain.model.Order;
import br.com.postech.techchallange_order.domain.ports.in.CheckouUseCase;
import br.com.postech.techchallange_order.infrastructure.adapters.in.rest.dto.CheckoutRequest;
import br.com.postech.techchallange_order.infrastructure.adapters.in.rest.dto.response.CheckoutResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CheckoutService implements CheckouUseCase {

	private final OrderService orderService;

	@Override
	public CheckoutResponse processarCheckout(CheckoutRequest request) {
		Order order = this.orderService.createOrder(request);

		CheckoutResponse response = new CheckoutResponse();
		response.setIdPedido(order.getId());
		response.setStatus(order.getStatus().getName());

		return response;
	}

}

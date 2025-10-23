package br.com.postech.techchallange_order.application.services;

import org.springframework.stereotype.Service;

import br.com.postech.techchallange_order.domain.model.Checkout;
import br.com.postech.techchallange_order.domain.ports.in.CheckouUseCase;
import br.com.postech.techchallange_order.infrastructure.adapters.in.rest.dto.CheckoutRequest;

@Service
public class CheckoutService implements CheckouUseCase {

	@Override
	public Checkout processarCheckout(CheckoutRequest request) {
		return new Checkout();
	}

}

package br.com.postech.techchallange_order.application.services;

import org.springframework.stereotype.Service;

import br.com.postech.techchallange_order.domain.ports.in.CheckouUseCase;
import br.com.postech.techchallange_order.infrastructure.adapters.in.rest.dto.CheckoutRequest;
import br.com.postech.techchallange_order.infrastructure.adapters.in.rest.dto.response.CheckoutResponse;

@Service
public class CheckoutService implements CheckouUseCase {

	@Override
	public CheckoutResponse processarCheckout(CheckoutRequest request) {
		return new CheckoutResponse();
	}

}

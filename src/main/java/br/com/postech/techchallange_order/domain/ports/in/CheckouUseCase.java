package br.com.postech.techchallange_order.domain.ports.in;

import br.com.postech.techchallange_order.domain.model.Checkout;
import br.com.postech.techchallange_order.infrastructure.adapters.in.rest.dto.CheckoutRequest;

public interface CheckouUseCase {
	Checkout processarCheckout(CheckoutRequest request);
}

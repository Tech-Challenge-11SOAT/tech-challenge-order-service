package br.com.postech.techchallange_order.domain.ports.in;

import br.com.postech.techchallange_order.infrastructure.adapters.in.rest.dto.CheckoutRequest;
import br.com.postech.techchallange_order.infrastructure.adapters.in.rest.dto.response.CheckoutResponse;

public interface CheckouUseCase {
	CheckoutResponse processarCheckout(CheckoutRequest request);
}

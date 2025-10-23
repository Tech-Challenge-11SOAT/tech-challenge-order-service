package br.com.postech.techchallange_order.domain.ports.out;

import br.com.postech.techchallange_order.infrastructure.adapters.in.rest.dto.response.CheckoutResponse;

public interface PaymentGatewayPort {
	CheckoutResponse processPayment(CheckoutResponse checkout);

	CheckoutResponse getPaymentStatus(Long paymentId);
}
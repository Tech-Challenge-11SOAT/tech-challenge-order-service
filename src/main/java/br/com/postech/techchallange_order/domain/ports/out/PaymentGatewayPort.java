package br.com.postech.techchallange_order.domain.ports.out;

import br.com.postech.techchallange_order.domain.model.Checkout;

public interface PaymentGatewayPort {
	Checkout processPayment(Checkout checkout);

	Checkout getPaymentStatus(Long paymentId);
}
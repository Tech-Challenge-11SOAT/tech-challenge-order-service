package br.com.postech.techchallange_order.domain.ports.in;

import br.com.postech.techchallange_order.domain.model.PaymentTransaction;

public interface PaymentUseCase {
	void processPaymentTransaction(PaymentTransaction transaction);
}

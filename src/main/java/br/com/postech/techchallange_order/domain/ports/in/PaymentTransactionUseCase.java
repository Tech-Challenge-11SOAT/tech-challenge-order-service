package br.com.postech.techchallange_order.domain.ports.in;

import br.com.postech.techchallange_order.domain.model.PaymentTransaction;

public interface PaymentTransactionUseCase {
	void createPaymentTransaction(PaymentTransaction transaction);
}

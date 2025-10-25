package br.com.postech.techchallange_order.application.services;

import java.time.Instant;

import org.springframework.stereotype.Service;

import br.com.postech.techchallange_order.domain.enums.StatusPagamentoEnum;
import br.com.postech.techchallange_order.domain.model.Order;
import br.com.postech.techchallange_order.domain.model.PaymentTransaction;
import br.com.postech.techchallange_order.domain.ports.in.CheckouUseCase;
import br.com.postech.techchallange_order.domain.ports.in.OrderStatusHistoryUseCase;
import br.com.postech.techchallange_order.domain.ports.in.OrderUseCase;
import br.com.postech.techchallange_order.domain.ports.in.PaymentTransactionUseCase;
import br.com.postech.techchallange_order.infrastructure.adapters.in.rest.dto.CheckoutRequest;
import br.com.postech.techchallange_order.infrastructure.adapters.in.rest.dto.response.CheckoutResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CheckoutService implements CheckouUseCase {

	private final OrderUseCase orderUseCase;
	private final OrderStatusHistoryUseCase orderStatusHistoryUseCase;
	private final PaymentTransactionUseCase paymentTransactionUseCase;

	@Override
	public CheckoutResponse processarCheckout(CheckoutRequest request) {
		Order order = this.orderUseCase.createOrder(request);

		this.processTransaction(order);
		this.recordOrderStatusHistory(order);

		CheckoutResponse response = new CheckoutResponse();
		response.setIdPedido(order.getId());
		response.setStatus(order.getStatus().getName());

		return response;
	}

	private void processTransaction(Order order) {
		PaymentTransaction orderTransaction = this.createOrderTransaction(order);
		this.paymentTransactionUseCase.createPaymentTransaction(orderTransaction);
	}

	private PaymentTransaction createOrderTransaction(Order order) {
		PaymentTransaction orderTransaction = new PaymentTransaction();
		orderTransaction.setOrderId(order.getId());
		orderTransaction.setAmount(order.getPayment().getTotalAmount());
		orderTransaction.setPaymentMethod(order.getPayment().getPaymentMethod());
		orderTransaction.setStatus(StatusPagamentoEnum.PENDENTE.getStatus());
		orderTransaction.setCreatedAt(Instant.now());

		return orderTransaction;
	}

	private void recordOrderStatusHistory(Order order) {
		this.orderStatusHistoryUseCase.recordStatusChange(order, null);
	}

}

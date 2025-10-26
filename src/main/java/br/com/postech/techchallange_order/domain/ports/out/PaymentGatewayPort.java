package br.com.postech.techchallange_order.domain.ports.out;

import br.com.postech.techchallange_order.domain.model.Order;

public interface PaymentGatewayPort {
	/**
	 * Create a payment order in the payment gateway
	 * 
	 * @param order      The order to create payment for
	 * @param payerEmail The email of the payer
	 * @return The updated order with payment information
	 */
	Order createPaymentOrder(Order order, String payerEmail);

	/**
	 * Get the payment status from the gateway
	 * 
	 * @param orderId The order ID to check status for
	 * @return The updated order with current payment status
	 */
	Order getPaymentStatus(String orderId);
}
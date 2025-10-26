package br.com.postech.techchallange_order.application.services;

import org.springframework.stereotype.Service;

import br.com.postech.techchallange_order.domain.model.Order;
import br.com.postech.techchallange_order.domain.ports.out.CustomerServicePort;
import br.com.postech.techchallange_order.domain.ports.out.PaymentGatewayPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MercadoPagoService {

	private final PaymentGatewayPort paymentGateway;
	private final CustomerServicePort customerService;

	public Order processPaymentWithMercadoPago(Order order) {
		log.info("Processando pagamento MercadoPago para pedido {}", order.getId());

		// Get customer email
		String email = customerService.getEmailByCustomerId(order.getCustomerId());
		log.info("E-mail do cliente recuperado: {}", email);

		// Create payment order in MercadoPago
		Order updatedOrder = paymentGateway.createPaymentOrder(order, email);

		log.info("Pagamento MercadoPago processado para pedido {}", order.getId());
		return updatedOrder;
	}
}

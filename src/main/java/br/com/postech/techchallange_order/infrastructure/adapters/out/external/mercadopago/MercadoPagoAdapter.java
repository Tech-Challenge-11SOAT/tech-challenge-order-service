package br.com.postech.techchallange_order.infrastructure.adapters.out.external.mercadopago;

import org.springframework.stereotype.Component;

import br.com.postech.techchallange_order.domain.ports.out.PaymentGatewayPort;
import br.com.postech.techchallange_order.infrastructure.adapters.in.rest.dto.response.CheckoutResponse;
import br.com.postech.techchallange_order.infrastructure.adapters.out.external.mercadopago.dto.OrderResponseDTO;

@Component
public class MercadoPagoAdapter implements PaymentGatewayPort {

	@Override
	public CheckoutResponse processPayment(CheckoutResponse checkout) {
		// Implementar a chamada para a API do Mercado Pago
		// Converter o resultado (OrderResponseDTO) para o modelo de domínio (Checkout)
		return null; // Implementar a lógica real
	}

	@Override
	public CheckoutResponse getPaymentStatus(Long paymentId) {
		// Implementar a chamada para a API do Mercado Pago
		// Converter o resultado (OrderResponseDTO) para o modelo de domínio (Checkout)
		return null; // Implementar a lógica real
	}

	private CheckoutResponse mapToCheckout(OrderResponseDTO orderResponse) {
		// Implementar o mapeamento do DTO para o modelo de domínio
		return null; // Implementar a lógica real
	}
}
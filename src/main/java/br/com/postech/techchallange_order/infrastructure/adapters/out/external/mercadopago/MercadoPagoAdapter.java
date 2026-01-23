package br.com.postech.techchallange_order.infrastructure.adapters.out.external.mercadopago;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.postech.techchallange_order.domain.model.Order;
import br.com.postech.techchallange_order.domain.ports.out.PaymentGatewayPort;
import br.com.postech.techchallange_order.infrastructure.adapters.out.external.mercadopago.dto.MercadoPagoOrderRequest;
import br.com.postech.techchallange_order.infrastructure.adapters.out.external.mercadopago.dto.MercadoPagoOrderResponse;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class MercadoPagoAdapter implements PaymentGatewayPort {

	@Value("${mercadopago.api.access-token}")
	private String accessToken;

	@Value("${mercadopago.api.user-id:TEST_USER_ID}")
	private String userId;

	@Value("${mercadopago.api.pos-id:SUC001POS001}")
	private String externalPosId;

	@Value("${mercadopago.options.integration-active:false}")
	private Boolean integrationActive;

	private final MercadoPagoFeignClient mercadoPagoFeignClient;

	@Override
	public Order createPaymentOrder(Order order, String payerEmail) {
		if (!integrationActive) {
			log.info("Integração com MercadoPago está desabilitada");
			return order;
		}

		try {
			MercadoPagoOrderRequest request = this.buildRequest(order, payerEmail);
			String authHeader = "Bearer " + accessToken;
			String idempotencyKey = UUID.randomUUID().toString();

			MercadoPagoOrderResponse response = mercadoPagoFeignClient.createInStoreOrder(
					authHeader,
					idempotencyKey,
					request);

			if (response != null) {
				Order.MercadoPagoInfo mpInfo = new Order.MercadoPagoInfo();
				mpInfo.setOrderId(response.getId());
				mpInfo.setQrCode(this.getQRCode(response));
				mpInfo.setQrCodeBase64(this.getQRCodeBase64(response));
				mpInfo.setExternalReference(order.getId());
				mpInfo.setStatus("pending");
				mpInfo.setStatusDetail("Pedido realizado. Pagamento pendente.");
				mpInfo.setTicketUrl(this.getTicketUrl(response));

				if (order.getPayment() != null) {
					order.getPayment().setMercadoPagoInfo(mpInfo);
				}
			}

			return order;
		} catch (FeignException e) {
			log.error("Erro ao criar pedido no MercadoPago: {}", e.getMessage(), e);
			return order;
		} catch (Exception e) {
			log.error("Erro inesperado ao criar pedido no MercadoPago", e);
			return order;
		}
	}

	@Override
	public Order getPaymentStatus(String orderId) {
		// TODO: implement status check
		return null;
	}

	private MercadoPagoOrderRequest buildRequest(Order order, String payerEmail) {
		MercadoPagoOrderRequest.PaymentMethod paymentMethod = MercadoPagoOrderRequest.PaymentMethod.builder()
				.id(order.getPayment().getPaymentMethod().toLowerCase())
				.type("bank_transfer")
				.build();

		MercadoPagoOrderRequest.Payment payment = MercadoPagoOrderRequest.Payment.builder()
				.amount(order.getPayment().getTotalAmount().toString())
				.paymentMethod(paymentMethod)
				.expirationTime("PT30M")
				.build();

		MercadoPagoOrderRequest.Transactions transactions = MercadoPagoOrderRequest.Transactions.builder()
				.payments(List.of(payment))
				.build();

		MercadoPagoOrderRequest.Payer payer = MercadoPagoOrderRequest.Payer.builder()
				.email(payerEmail)
				.build();

		payer.setFirstName(MercadoPagoConstants.MERCADO_PAGO_FIRST_NAME);
		payer.setEmail(MercadoPagoConstants.MERCADO_PAGO_TEST_EMAIL);

		return MercadoPagoOrderRequest.builder()
				.type("online")
				.totalAmount(order.getPayment().getTotalAmount().toString())
				.externalReference(this.getExternalReference(order.getId()))
				.processingMode("automatic")
				.transactions(transactions)
				.payer(payer)
				.build();
	}

	private String getExternalReference(String orderId) {
		return String.format("pedido_%s_%d", orderId, System.currentTimeMillis());
	}

	private String getQRCode(MercadoPagoOrderResponse response) {
		return response.getTransactions().getPayments().get(0).getPaymentMethod().getQrCode();
	}

	private String getQRCodeBase64(MercadoPagoOrderResponse response) {
		return response.getTransactions().getPayments().get(0).getPaymentMethod().getQrCodeBase64();
	}

	private String getTicketUrl(MercadoPagoOrderResponse response) {
		return response.getTransactions().getPayments().get(0).getPaymentMethod().getTicketUrl();
	}
}
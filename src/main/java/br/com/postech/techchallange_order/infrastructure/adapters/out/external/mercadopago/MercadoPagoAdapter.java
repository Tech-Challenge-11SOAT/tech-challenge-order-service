package br.com.postech.techchallange_order.infrastructure.adapters.out.external.mercadopago;

import java.math.BigDecimal;
import java.util.stream.Collectors;

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
			MercadoPagoOrderRequest request = buildRequest(order, payerEmail);
			String authHeader = "Bearer " + accessToken;

			MercadoPagoOrderResponse response = mercadoPagoFeignClient.createInStoreOrder(
					authHeader,
					userId,
					externalPosId,
					request);

			if (response != null) {
				Order.MercadoPagoInfo mpInfo = new Order.MercadoPagoInfo();
				mpInfo.setOrderId(response.getInStoreOrderId());
				mpInfo.setQrCode(response.getQrData());
				mpInfo.setExternalReference(order.getId());
				mpInfo.setStatus("pending");

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
		return MercadoPagoOrderRequest.builder()
				.externalReference(order.getId())
				.title("Pedido " + order.getId())
				.description("Pedido realizado na lanchonete")
				.totalAmount(order.getPayment().getTotalAmount())
				.items(order.getItems().stream()
						.map(item -> MercadoPagoOrderRequest.Item.builder()
								.skuNumber(String.valueOf(item.getProductId()))
								.category("food")
								.title("Produto " + item.getProductId())
								.description("Produto")
								.unitPrice(item.getUnitPrice())
								.quantity(item.getQuantity())
								.unitMeasure("unit")
								.totalAmount(item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
								.build())
						.collect(Collectors.toList()))
				.payer(MercadoPagoOrderRequest.Payer.builder()
						.email(payerEmail)
						.build())
				.build();
	}
}
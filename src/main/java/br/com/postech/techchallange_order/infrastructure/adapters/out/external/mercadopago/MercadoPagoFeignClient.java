package br.com.postech.techchallange_order.infrastructure.adapters.out.external.mercadopago;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import br.com.postech.techchallange_order.infrastructure.adapters.out.external.mercadopago.dto.MercadoPagoOrderRequest;
import br.com.postech.techchallange_order.infrastructure.adapters.out.external.mercadopago.dto.MercadoPagoOrderResponse;

@FeignClient(name = "mercadopago-api", url = "${mercadopago.api.base-url}")
public interface MercadoPagoFeignClient {

	@PostMapping(value = "/instore/orders/qr/seller/collectors/{userId}/pos/{externalPosId}/qrs", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	MercadoPagoOrderResponse createInStoreOrder(
			@RequestHeader("Authorization") String authorization,
			@PathVariable("userId") String userId,
			@PathVariable("externalPosId") String externalPosId,
			@RequestBody MercadoPagoOrderRequest request);
}

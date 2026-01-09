package br.com.postech.techchallange_order.infrastructure.adapters.out.external.mercadopago.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MercadoPagoOrderResponse {

	@JsonProperty("in_store_order_id")
	private String inStoreOrderId;

	@JsonProperty("qr_data")
	private String qrData;
}

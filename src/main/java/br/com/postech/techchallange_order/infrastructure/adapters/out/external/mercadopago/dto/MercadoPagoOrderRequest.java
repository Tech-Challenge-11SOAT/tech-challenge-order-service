package br.com.postech.techchallange_order.infrastructure.adapters.out.external.mercadopago.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MercadoPagoOrderRequest {

	private String type;

	@JsonProperty("total_amount")
	private String totalAmount;

	@JsonProperty("external_reference")
	private String externalReference;

	@JsonProperty("processing_mode")
	private String processingMode;

	private Transactions transactions;

	private Payer payer;

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Transactions {
		private List<Payment> payments;
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Payment {
		private String amount;

		@JsonProperty("payment_method")
		private PaymentMethod paymentMethod;

		@JsonProperty("expiration_time")
		private String expirationTime;
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class PaymentMethod {
		private String id;
		private String type;
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Payer {

		@JsonProperty("first_name")
		private String firstName;
		private String email;
	}

}

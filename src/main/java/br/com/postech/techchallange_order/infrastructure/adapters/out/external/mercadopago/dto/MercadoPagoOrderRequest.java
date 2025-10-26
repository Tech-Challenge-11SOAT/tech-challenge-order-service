package br.com.postech.techchallange_order.infrastructure.adapters.out.external.mercadopago.dto;

import java.math.BigDecimal;
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

	@JsonProperty("external_reference")
	private String externalReference;

	@JsonProperty("title")
	private String title;

	@JsonProperty("description")
	private String description;

	@JsonProperty("notification_url")
	private String notificationUrl;

	@JsonProperty("total_amount")
	private BigDecimal totalAmount;

	@JsonProperty("items")
	private List<Item> items;

	@JsonProperty("payer")
	private Payer payer;

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Item {
		@JsonProperty("sku_number")
		private String skuNumber;

		@JsonProperty("category")
		private String category;

		@JsonProperty("title")
		private String title;

		@JsonProperty("description")
		private String description;

		@JsonProperty("unit_price")
		private BigDecimal unitPrice;

		@JsonProperty("quantity")
		private Integer quantity;

		@JsonProperty("unit_measure")
		private String unitMeasure;

		@JsonProperty("total_amount")
		private BigDecimal totalAmount;
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Payer {
		@JsonProperty("email")
		private String email;
	}
}

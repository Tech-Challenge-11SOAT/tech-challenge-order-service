package br.com.postech.techchallange_order.infrastructure.adapters.out.mongodb.model;

import java.time.Instant;
import java.util.List;

import org.bson.types.Decimal128;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "orders")
public class OrderDocument {
	@Id
	private ObjectId id;

	private Long orderId;
	private Long customerId;
	private Instant orderDate;

	private Status status;
	private Integer queuePosition;
	private List<Item> items;
	private Payment payment;

	@CreatedDate
	private Instant createdAt;

	@LastModifiedDate
	private Instant updatedAt;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Status {
		private Long id;
		private String name;
		private Instant updatedAt;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Item {
		private Long productId;
		private Integer quantity;
		private Decimal128 unitPrice;
		private Decimal128 subtotal;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Payment {
		private Long paymentId;
		private Decimal128 totalAmount;
		private String paymentMethod;
		private PaymentStatus status;
		private Instant paymentDate;
		private MercadoPagoInfo mercadoPagoInfo;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class PaymentStatus {
		private Long id;
		private String name;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class MercadoPagoInfo {
		private String orderId;
		private String status;
		private String statusDetail;
		private String externalReference;
		private String qrCode;
		private String qrCodeBase64;
		private String ticketUrl;
	}
}

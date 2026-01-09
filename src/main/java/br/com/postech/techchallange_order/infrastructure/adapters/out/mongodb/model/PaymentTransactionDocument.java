package br.com.postech.techchallange_order.infrastructure.adapters.out.mongodb.model;

import java.time.Instant;
import java.util.Map;

import org.bson.types.Decimal128;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "payment_transactions")
public class PaymentTransactionDocument {

	@Id
	private ObjectId id;

	@Field("orderId")
	private ObjectId orderId;

	@Field("transactionId")
	private String transactionId;

	@Field("amount")
	private Decimal128 amount;

	@Field("paymentMethod")
	private String paymentMethod;

	@Field("status")
	private String status;

	@Field("gatewayResponse")
	private Map<String, Object> gatewayResponse;

	@CreatedDate
	@Field("createdAt")
	private Instant createdAt;

	@LastModifiedDate
	@Field("updatedAt")
	private Instant updatedAt;
}
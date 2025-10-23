package br.com.postech.techchallange_order.infrastructure.adapters.out.mongodb.model;

import java.time.Instant;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "order_history")
public class OrderHistoryDocument {
	@Id
	private ObjectId id;

	@Field("order_id")
	private ObjectId orderId;

	private Status status;

	@CreatedDate
	@Field("created_at")
	private Instant createdAt;

	@Field("previous_status")
	private Status previousStatus;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Status {
		private Long id;
		private String name;
	}
}

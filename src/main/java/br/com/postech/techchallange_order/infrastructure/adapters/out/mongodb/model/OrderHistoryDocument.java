package br.com.postech.techchallange_order.infrastructure.adapters.out.mongodb.model;

import java.time.Instant;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "order_status_history")
public class OrderHistoryDocument {
	@Id
	private ObjectId id;

	@Field("orderId")
	private ObjectId orderId;

	private Status status;

	@CreatedDate
	@Field("createdAt")
	private Instant createdAt;

	@Field("previousStatus")
	private Status previousStatus;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Status {
		private Long id;
		private String name;
	}
}

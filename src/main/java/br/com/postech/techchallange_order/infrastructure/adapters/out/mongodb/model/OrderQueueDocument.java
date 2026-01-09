package br.com.postech.techchallange_order.infrastructure.adapters.out.mongodb.model;

import java.time.Instant;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "order_queue")
public class OrderQueueDocument {
	@Id
	private ObjectId id;

	private ObjectId orderId;
	private String customerId;

	@CreatedDate
	private Instant createdAt;

	private String status;
}

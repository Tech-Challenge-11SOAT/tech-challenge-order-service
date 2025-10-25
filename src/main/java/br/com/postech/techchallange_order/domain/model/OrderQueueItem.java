package br.com.postech.techchallange_order.domain.model;

import java.time.Instant;

public class OrderQueueItem {
	private String id;
	private String orderId;
	private Long customerId; // nullable for anonymous
	private Instant createdAt;
	private String status;

	public OrderQueueItem() {
	}

	public OrderQueueItem(String id, String orderId, Long customerId, Instant createdAt, String status) {
		this.id = id;
		this.orderId = orderId;
		this.customerId = customerId;
		this.createdAt = createdAt;
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}

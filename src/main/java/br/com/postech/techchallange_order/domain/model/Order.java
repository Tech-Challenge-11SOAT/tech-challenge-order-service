package br.com.postech.techchallange_order.domain.model;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
	private String id;
	private String customerId;
	private List<OrderItem> items;
	private OrderStatus status;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	// Constructor, getters, setters, and other methods
}

class OrderItem {
	private String productId;
	private int quantity;
	private double price;

	// Constructor, getters, setters
}

enum OrderStatus {
	CREATED,
	CONFIRMED,
	IN_PREPARATION,
	READY,
	COMPLETED,
	CANCELLED
}
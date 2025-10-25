package br.com.postech.techchallange_order.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

public class PaymentTransaction {
	private String id;
	private String orderId;
	private String transactionId;
	private BigDecimal amount;
	private String paymentMethod;
	private String status;
	private Map<String, Object> gatewayResponse;
	private Instant createdAt;
	private Instant updatedAt;

	public PaymentTransaction() {
	}

	public PaymentTransaction(String id, String orderId, String transactionId, BigDecimal amount,
			String paymentMethod, String status, Map<String, Object> gatewayResponse,
			Instant createdAt, Instant updatedAt) {
		this.id = id;
		this.orderId = orderId;
		this.transactionId = transactionId;
		this.amount = amount;
		this.paymentMethod = paymentMethod;
		this.status = status;
		this.gatewayResponse = gatewayResponse;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
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

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Map<String, Object> getGatewayResponse() {
		return gatewayResponse;
	}

	public void setGatewayResponse(Map<String, Object> gatewayResponse) {
		this.gatewayResponse = gatewayResponse;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Instant updatedAt) {
		this.updatedAt = updatedAt;
	}
}
package br.com.postech.techchallange_order.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Order {
	private String id;
	private Long orderId;
	private String customerId;
	private Instant orderDate;
	private Status status;
	private Integer queuePosition;
	private List<Item> items = new ArrayList<>();
	private Payment payment;
	private Instant createdAt;
	private Instant updatedAt;

	public Order() {
	}

	private Order(Builder builder) {
		this.id = builder.id;
		this.orderId = builder.orderId;
		this.customerId = builder.customerId;
		this.orderDate = builder.orderDate;
		this.status = builder.status;
		this.queuePosition = builder.queuePosition;
		this.items = builder.items != null ? builder.items : new ArrayList<>();
		this.payment = builder.payment;
		this.createdAt = builder.createdAt;
		this.updatedAt = builder.updatedAt;
	}

	public static Builder builder() {
		return new Builder();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Instant getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Instant orderDate) {
		this.orderDate = orderDate;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Integer getQueuePosition() {
		return queuePosition;
	}

	public void setQueuePosition(Integer queuePosition) {
		this.queuePosition = queuePosition;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items != null ? items : new ArrayList<>();
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
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

	public static class Status {
		private Long id;
		private String name;
		private Instant updatedAt;

		public Status() {
		}

		public Status(Long id, String name, Instant updatedAt) {
			this.id = id;
			this.name = name;
			this.updatedAt = updatedAt;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Instant getUpdatedAt() {
			return updatedAt;
		}

		public void setUpdatedAt(Instant updatedAt) {
			this.updatedAt = updatedAt;
		}
	}

	public static class Item {
		private Long productId;
		private Integer quantity;
		private BigDecimal unitPrice;
		private BigDecimal subtotal;

		public Item() {
		}

		public Item(Long productId, Integer quantity, BigDecimal unitPrice, BigDecimal subtotal) {
			this.productId = productId;
			this.quantity = quantity;
			this.unitPrice = unitPrice;
			this.subtotal = subtotal;
		}

		public Long getProductId() {
			return productId;
		}

		public void setProductId(Long productId) {
			this.productId = productId;
		}

		public Integer getQuantity() {
			return quantity;
		}

		public void setQuantity(Integer quantity) {
			this.quantity = quantity;
		}

		public BigDecimal getUnitPrice() {
			return unitPrice;
		}

		public void setUnitPrice(BigDecimal unitPrice) {
			this.unitPrice = unitPrice;
		}

		public BigDecimal getSubtotal() {
			return subtotal;
		}

		public void setSubtotal(BigDecimal subtotal) {
			this.subtotal = subtotal;
		}
	}

	public static class Payment {
		private Long paymentId;
		private BigDecimal totalAmount;
		private String paymentMethod;
		private PaymentStatus status;
		private Instant paymentDate;
		private MercadoPagoInfo mercadoPagoInfo;

		public Payment() {
		}

		public Payment(Long paymentId, BigDecimal totalAmount, String paymentMethod, PaymentStatus status,
				Instant paymentDate, MercadoPagoInfo mercadoPagoInfo) {
			this.paymentId = paymentId;
			this.totalAmount = totalAmount;
			this.paymentMethod = paymentMethod;
			this.status = status;
			this.paymentDate = paymentDate;
			this.mercadoPagoInfo = mercadoPagoInfo;
		}

		public Long getPaymentId() {
			return paymentId;
		}

		public void setPaymentId(Long paymentId) {
			this.paymentId = paymentId;
		}

		public BigDecimal getTotalAmount() {
			return totalAmount;
		}

		public void setTotalAmount(BigDecimal totalAmount) {
			this.totalAmount = totalAmount;
		}

		public String getPaymentMethod() {
			return paymentMethod;
		}

		public void setPaymentMethod(String paymentMethod) {
			this.paymentMethod = paymentMethod;
		}

		public PaymentStatus getStatus() {
			return status;
		}

		public void setStatus(PaymentStatus status) {
			this.status = status;
		}

		public Instant getPaymentDate() {
			return paymentDate;
		}

		public void setPaymentDate(Instant paymentDate) {
			this.paymentDate = paymentDate;
		}

		public MercadoPagoInfo getMercadoPagoInfo() {
			return mercadoPagoInfo;
		}

		public void setMercadoPagoInfo(MercadoPagoInfo mercadoPagoInfo) {
			this.mercadoPagoInfo = mercadoPagoInfo;
		}

		public static class PaymentStatus {
			private Long id;
			private String name;

			public PaymentStatus() {
			}

			public PaymentStatus(Long id, String name) {
				this.id = id;
				this.name = name;
			}

			public Long getId() {
				return id;
			}

			public void setId(Long id) {
				this.id = id;
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}
		}
	}

	public static class MercadoPagoInfo {
		private String orderId;
		private String status;
		private String statusDetail;
		private String externalReference;
		private String qrCode;
		private String qrCodeBase64;
		private String ticketUrl;

		public MercadoPagoInfo() {
		}

		public MercadoPagoInfo(String orderId, String status, String statusDetail, String externalReference,
				String qrCode, String qrCodeBase64, String ticketUrl) {
			this.orderId = orderId;
			this.status = status;
			this.statusDetail = statusDetail;
			this.externalReference = externalReference;
			this.qrCode = qrCode;
			this.qrCodeBase64 = qrCodeBase64;
			this.ticketUrl = ticketUrl;
		}

		public String getOrderId() {
			return orderId;
		}

		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getStatusDetail() {
			return statusDetail;
		}

		public void setStatusDetail(String statusDetail) {
			this.statusDetail = statusDetail;
		}

		public String getExternalReference() {
			return externalReference;
		}

		public void setExternalReference(String externalReference) {
			this.externalReference = externalReference;
		}

		public String getQrCode() {
			return qrCode;
		}

		public void setQrCode(String qrCode) {
			this.qrCode = qrCode;
		}

		public String getQrCodeBase64() {
			return qrCodeBase64;
		}

		public void setQrCodeBase64(String qrCodeBase64) {
			this.qrCodeBase64 = qrCodeBase64;
		}

		public String getTicketUrl() {
			return ticketUrl;
		}

		public void setTicketUrl(String ticketUrl) {
			this.ticketUrl = ticketUrl;
		}
	}

	public static class Builder {
		private String id;
		private Long orderId;
		private String customerId;
		private Instant orderDate;
		private Status status;
		private Integer queuePosition;
		private List<Item> items;
		private Payment payment;
		private Instant createdAt;
		private Instant updatedAt;

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder orderId(Long orderId) {
			this.orderId = orderId;
			return this;
		}

		public Builder customerId(String customerId) {
			this.customerId = customerId;
			return this;
		}

		public Builder orderDate(Instant orderDate) {
			this.orderDate = orderDate;
			return this;
		}

		public Builder status(Status status) {
			this.status = status;
			return this;
		}

		public Builder queuePosition(Integer queuePosition) {
			this.queuePosition = queuePosition;
			return this;
		}

		public Builder items(List<Item> items) {
			this.items = items;
			return this;
		}

		public Builder payment(Payment payment) {
			this.payment = payment;
			return this;
		}

		public Builder createdAt(Instant createdAt) {
			this.createdAt = createdAt;
			return this;
		}

		public Builder updatedAt(Instant updatedAt) {
			this.updatedAt = updatedAt;
			return this;
		}

		public Order build() {
			return new Order(this);
		}
	}
}
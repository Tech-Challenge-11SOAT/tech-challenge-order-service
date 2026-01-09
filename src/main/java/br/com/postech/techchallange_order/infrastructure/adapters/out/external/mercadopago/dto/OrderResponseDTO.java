package br.com.postech.techchallange_order.infrastructure.adapters.out.external.mercadopago.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResponseDTO {

	private String orderId;
	private String type;
	private String status;
	private String statusDetail;
	private String externalReference;
	private BigDecimal totalAmount;
	private String processingMode;
	private String countryCode;
	private String userId;
	private String captureMode;
	private String currency;
	private OffsetDateTime createdDate;
	private OffsetDateTime lastUpdatedDate;

	// Integration data
	private String applicationId;

	// Payment data
	private String paymentId;
	private String paymentStatus;
	private String paymentStatusDetail;
	private BigDecimal paymentAmount;
	private String expirationTime;
	private OffsetDateTime dateOfExpiration;
	private String referenceId;
}
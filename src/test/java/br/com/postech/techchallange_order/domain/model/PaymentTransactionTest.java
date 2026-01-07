package br.com.postech.techchallange_order.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTransactionTest {

	@Test
	void shouldCreatePaymentTransactionWithNoArgsConstructor() {
		PaymentTransaction transaction = new PaymentTransaction();

		assertNotNull(transaction);
		assertNull(transaction.getId());
		assertNull(transaction.getOrderId());
		assertNull(transaction.getTransactionId());
		assertNull(transaction.getAmount());
		assertNull(transaction.getPaymentMethod());
		assertNull(transaction.getStatus());
		assertNull(transaction.getGatewayResponse());
		assertNull(transaction.getCreatedAt());
		assertNull(transaction.getUpdatedAt());
	}

	@Test
	void shouldCreatePaymentTransactionWithAllArgsConstructor() {
		Instant now = Instant.now();
		Map<String, Object> gatewayResponse = new HashMap<>();
		gatewayResponse.put("code", "200");
		gatewayResponse.put("message", "Success");

		PaymentTransaction transaction = new PaymentTransaction(
			"id-123", "order-456", "trans-789", BigDecimal.valueOf(100.50),
			"PIX", "FINALIZADO", gatewayResponse, now, now
		);

		assertEquals("id-123", transaction.getId());
		assertEquals("order-456", transaction.getOrderId());
		assertEquals("trans-789", transaction.getTransactionId());
		assertEquals(BigDecimal.valueOf(100.50), transaction.getAmount());
		assertEquals("PIX", transaction.getPaymentMethod());
		assertEquals("FINALIZADO", transaction.getStatus());
		assertEquals(gatewayResponse, transaction.getGatewayResponse());
		assertEquals(now, transaction.getCreatedAt());
		assertEquals(now, transaction.getUpdatedAt());
	}

	@Test
	void shouldSetAndGetId() {
		PaymentTransaction transaction = new PaymentTransaction();
		transaction.setId("payment-001");

		assertEquals("payment-001", transaction.getId());
	}

	@Test
	void shouldSetAndGetOrderId() {
		PaymentTransaction transaction = new PaymentTransaction();
		transaction.setOrderId("order-002");

		assertEquals("order-002", transaction.getOrderId());
	}

	@Test
	void shouldSetAndGetTransactionId() {
		PaymentTransaction transaction = new PaymentTransaction();
		transaction.setTransactionId("trans-003");

		assertEquals("trans-003", transaction.getTransactionId());
	}

	@Test
	void shouldSetAndGetAmount() {
		PaymentTransaction transaction = new PaymentTransaction();
		transaction.setAmount(BigDecimal.valueOf(250.75));

		assertEquals(BigDecimal.valueOf(250.75), transaction.getAmount());
	}

	@Test
	void shouldSetAndGetPaymentMethod() {
		PaymentTransaction transaction = new PaymentTransaction();
		transaction.setPaymentMethod("CARTAO");

		assertEquals("CARTAO", transaction.getPaymentMethod());
	}

	@Test
	void shouldSetAndGetStatus() {
		PaymentTransaction transaction = new PaymentTransaction();
		transaction.setStatus("PENDENTE");

		assertEquals("PENDENTE", transaction.getStatus());
	}

	@Test
	void shouldSetAndGetGatewayResponse() {
		PaymentTransaction transaction = new PaymentTransaction();
		Map<String, Object> response = new HashMap<>();
		response.put("status", "success");
		response.put("transactionId", "12345");

		transaction.setGatewayResponse(response);

		assertEquals(response, transaction.getGatewayResponse());
		assertEquals("success", transaction.getGatewayResponse().get("status"));
		assertEquals("12345", transaction.getGatewayResponse().get("transactionId"));
	}

	@Test
	void shouldSetAndGetCreatedAt() {
		PaymentTransaction transaction = new PaymentTransaction();
		Instant now = Instant.now();
		transaction.setCreatedAt(now);

		assertEquals(now, transaction.getCreatedAt());
	}

	@Test
	void shouldSetAndGetUpdatedAt() {
		PaymentTransaction transaction = new PaymentTransaction();
		Instant now = Instant.now();
		transaction.setUpdatedAt(now);

		assertEquals(now, transaction.getUpdatedAt());
	}

	@Test
	void shouldHandleNullGatewayResponse() {
		PaymentTransaction transaction = new PaymentTransaction();
		transaction.setGatewayResponse(null);

		assertNull(transaction.getGatewayResponse());
	}

	@Test
	void shouldHandleEmptyGatewayResponse() {
		PaymentTransaction transaction = new PaymentTransaction();
		Map<String, Object> emptyResponse = new HashMap<>();
		transaction.setGatewayResponse(emptyResponse);

		assertNotNull(transaction.getGatewayResponse());
		assertTrue(transaction.getGatewayResponse().isEmpty());
	}

	@Test
	void shouldHandleDifferentPaymentMethods() {
		PaymentTransaction transaction = new PaymentTransaction();

		transaction.setPaymentMethod("PIX");
		assertEquals("PIX", transaction.getPaymentMethod());

		transaction.setPaymentMethod("CARTAO");
		assertEquals("CARTAO", transaction.getPaymentMethod());

		transaction.setPaymentMethod("DINHEIRO");
		assertEquals("DINHEIRO", transaction.getPaymentMethod());
	}

	@Test
	void shouldHandleDifferentStatuses() {
		PaymentTransaction transaction = new PaymentTransaction();

		transaction.setStatus("PENDENTE");
		assertEquals("PENDENTE", transaction.getStatus());

		transaction.setStatus("FINALIZADO");
		assertEquals("FINALIZADO", transaction.getStatus());

		transaction.setStatus("ERRO");
		assertEquals("ERRO", transaction.getStatus());
	}
}


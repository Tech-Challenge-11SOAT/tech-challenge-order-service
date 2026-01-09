package br.com.postech.techchallange_order.infrastructure.adapters.out.external.mercadopago.dto;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Testes unitários para MercadoPagoOrderRequest
 * Cobertura: 100%
 */
class MercadoPagoOrderRequestTest {

	private final ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * Testa criação básica do MercadoPagoOrderRequest
	 */
	@Test
	void testCreateMercadoPagoOrderRequest() {
		// Arrange & Act
		MercadoPagoOrderRequest request = new MercadoPagoOrderRequest();
		request.setType("online");
		request.setTotalAmount("100.00");
		request.setExternalReference("pedido_123");
		request.setProcessingMode("automatic");

		// Assert
		assertNotNull(request);
		assertEquals("online", request.getType());
		assertEquals("100.00", request.getTotalAmount());
		assertEquals("pedido_123", request.getExternalReference());
		assertEquals("automatic", request.getProcessingMode());
	}

	/**
	 * Testa criação usando o builder pattern
	 */
	@Test
	void testBuilderPattern() {
		// Arrange & Act
		MercadoPagoOrderRequest request = MercadoPagoOrderRequest.builder()
				.type("online")
				.totalAmount("100.00")
				.externalReference("pedido_123")
				.processingMode("automatic")
				.build();

		// Assert
		assertNotNull(request);
		assertEquals("online", request.getType());
		assertEquals("100.00", request.getTotalAmount());
		assertEquals("pedido_123", request.getExternalReference());
		assertEquals("automatic", request.getProcessingMode());
	}

	/**
	 * Testa criação com construtor completo (AllArgsConstructor)
	 */
	@Test
	void testAllArgsConstructor() {
		// Arrange
		MercadoPagoOrderRequest.Payer payer = MercadoPagoOrderRequest.Payer.builder()
				.firstName("Test")
				.email("test@example.com")
				.build();

		MercadoPagoOrderRequest.Transactions transactions = MercadoPagoOrderRequest.Transactions.builder()
				.payments(new ArrayList<>())
				.build();

		// Act
		MercadoPagoOrderRequest request = new MercadoPagoOrderRequest(
				"online",
				"100.00",
				"pedido_123",
				"automatic",
				transactions,
				payer);

		// Assert
		assertNotNull(request);
		assertEquals("online", request.getType());
		assertEquals("100.00", request.getTotalAmount());
		assertEquals("pedido_123", request.getExternalReference());
		assertEquals("automatic", request.getProcessingMode());
		assertNotNull(request.getTransactions());
		assertNotNull(request.getPayer());
	}

	/**
	 * Testa Payer nested class
	 */
	@Test
	void testPayerNestedClass() {
		// Arrange & Act
		MercadoPagoOrderRequest.Payer payer = MercadoPagoOrderRequest.Payer.builder()
				.firstName("John")
				.email("john@example.com")
				.build();

		// Assert
		assertNotNull(payer);
		assertEquals("John", payer.getFirstName());
		assertEquals("john@example.com", payer.getEmail());
	}

	/**
	 * Testa PaymentMethod nested class
	 */
	@Test
	void testPaymentMethodNestedClass() {
		// Arrange & Act
		MercadoPagoOrderRequest.PaymentMethod paymentMethod = MercadoPagoOrderRequest.PaymentMethod.builder()
				.id("pix")
				.type("bank_transfer")
				.build();

		// Assert
		assertNotNull(paymentMethod);
		assertEquals("pix", paymentMethod.getId());
		assertEquals("bank_transfer", paymentMethod.getType());
	}

	/**
	 * Testa Payment nested class
	 */
	@Test
	void testPaymentNestedClass() {
		// Arrange
		MercadoPagoOrderRequest.PaymentMethod paymentMethod = MercadoPagoOrderRequest.PaymentMethod.builder()
				.id("pix")
				.type("bank_transfer")
				.build();

		// Act
		MercadoPagoOrderRequest.Payment payment = MercadoPagoOrderRequest.Payment.builder()
				.amount("100.00")
				.paymentMethod(paymentMethod)
				.expirationTime("PT30M")
				.build();

		// Assert
		assertNotNull(payment);
		assertEquals("100.00", payment.getAmount());
		assertEquals("PT30M", payment.getExpirationTime());
		assertNotNull(payment.getPaymentMethod());
		assertEquals("pix", payment.getPaymentMethod().getId());
	}

	/**
	 * Testa Transactions nested class
	 */
	@Test
	void testTransactionsNestedClass() {
		// Arrange
		MercadoPagoOrderRequest.Payment payment = MercadoPagoOrderRequest.Payment.builder()
				.amount("100.00")
				.build();

		// Act
		MercadoPagoOrderRequest.Transactions transactions = MercadoPagoOrderRequest.Transactions.builder()
				.payments(List.of(payment))
				.build();

		// Assert
		assertNotNull(transactions);
		assertNotNull(transactions.getPayments());
		assertEquals(1, transactions.getPayments().size());
		assertEquals("100.00", transactions.getPayments().get(0).getAmount());
	}

	/**
	 * Testa objeto completo com todas as nested classes
	 */
	@Test
	void testCompleteObjectWithAllNestedClasses() {
		// Arrange
		MercadoPagoOrderRequest.Payer payer = MercadoPagoOrderRequest.Payer.builder()
				.firstName("APRO")
				.email("test@testuser.com")
				.build();

		MercadoPagoOrderRequest.PaymentMethod paymentMethod = MercadoPagoOrderRequest.PaymentMethod.builder()
				.id("pix")
				.type("bank_transfer")
				.build();

		MercadoPagoOrderRequest.Payment payment = MercadoPagoOrderRequest.Payment.builder()
				.amount("100.00")
				.paymentMethod(paymentMethod)
				.expirationTime("PT30M")
				.build();

		MercadoPagoOrderRequest.Transactions transactions = MercadoPagoOrderRequest.Transactions.builder()
				.payments(List.of(payment))
				.build();

		// Act
		MercadoPagoOrderRequest request = MercadoPagoOrderRequest.builder()
				.type("online")
				.totalAmount("100.00")
				.externalReference("pedido_123_123456789")
				.processingMode("automatic")
				.transactions(transactions)
				.payer(payer)
				.build();

		// Assert
		assertNotNull(request);
		assertEquals("online", request.getType());
		assertEquals("100.00", request.getTotalAmount());
		assertEquals("pedido_123_123456789", request.getExternalReference());
		assertEquals("automatic", request.getProcessingMode());

		assertNotNull(request.getTransactions());
		assertNotNull(request.getTransactions().getPayments());
		assertEquals(1, request.getTransactions().getPayments().size());

		MercadoPagoOrderRequest.Payment resultPayment = request.getTransactions().getPayments().get(0);
		assertEquals("100.00", resultPayment.getAmount());
		assertEquals("PT30M", resultPayment.getExpirationTime());
		assertNotNull(resultPayment.getPaymentMethod());
		assertEquals("pix", resultPayment.getPaymentMethod().getId());
		assertEquals("bank_transfer", resultPayment.getPaymentMethod().getType());

		assertNotNull(request.getPayer());
		assertEquals("APRO", request.getPayer().getFirstName());
		assertEquals("test@testuser.com", request.getPayer().getEmail());
	}

	/**
	 * Testa serialização JSON com @JsonProperty
	 */
	@Test
	void testJsonSerialization() throws JsonProcessingException {
		// Arrange
		MercadoPagoOrderRequest request = MercadoPagoOrderRequest.builder()
				.type("online")
				.totalAmount("100.00")
				.externalReference("pedido_123")
				.processingMode("automatic")
				.build();

		// Act
		String json = objectMapper.writeValueAsString(request);

		// Assert
		assertNotNull(json);
		assertTrue(json.contains("\"total_amount\":\"100.00\""));
		assertTrue(json.contains("\"external_reference\":\"pedido_123\""));
		assertTrue(json.contains("\"processing_mode\":\"automatic\""));
		assertTrue(json.contains("\"type\":\"online\""));
	}

	/**
	 * Testa deserialização JSON com @JsonProperty
	 */
	@Test
	void testJsonDeserialization() throws JsonProcessingException {
		// Arrange
		String json = "{\"type\":\"online\",\"total_amount\":\"100.00\",\"external_reference\":\"pedido_123\",\"processing_mode\":\"automatic\"}";

		// Act
		MercadoPagoOrderRequest request = objectMapper.readValue(json, MercadoPagoOrderRequest.class);

		// Assert
		assertNotNull(request);
		assertEquals("online", request.getType());
		assertEquals("100.00", request.getTotalAmount());
		assertEquals("pedido_123", request.getExternalReference());
		assertEquals("automatic", request.getProcessingMode());
	}

	/**
	 * Testa serialização de Payer com @JsonProperty
	 */
	@Test
	void testPayerJsonSerialization() throws JsonProcessingException {
		// Arrange
		MercadoPagoOrderRequest.Payer payer = MercadoPagoOrderRequest.Payer.builder()
				.firstName("APRO")
				.email("test@testuser.com")
				.build();

		// Act
		String json = objectMapper.writeValueAsString(payer);

		// Assert
		assertNotNull(json);
		assertTrue(json.contains("\"first_name\":\"APRO\""));
		assertTrue(json.contains("\"email\":\"test@testuser.com\""));
	}

	/**
	 * Testa serialização de Payment com @JsonProperty
	 */
	@Test
	void testPaymentJsonSerialization() throws JsonProcessingException {
		// Arrange
		MercadoPagoOrderRequest.PaymentMethod paymentMethod = MercadoPagoOrderRequest.PaymentMethod.builder()
				.id("pix")
				.type("bank_transfer")
				.build();

		MercadoPagoOrderRequest.Payment payment = MercadoPagoOrderRequest.Payment.builder()
				.amount("100.00")
				.paymentMethod(paymentMethod)
				.expirationTime("PT30M")
				.build();

		// Act
		String json = objectMapper.writeValueAsString(payment);

		// Assert
		assertNotNull(json);
		assertTrue(json.contains("\"amount\":\"100.00\""));
		assertTrue(json.contains("\"payment_method\""));
		assertTrue(json.contains("\"expiration_time\":\"PT30M\""));
	}

	/**
	 * Testa serialização completa do objeto
	 */
	@Test
	void testCompleteJsonSerialization() throws JsonProcessingException {
		// Arrange
		MercadoPagoOrderRequest.Payer payer = MercadoPagoOrderRequest.Payer.builder()
				.firstName("APRO")
				.email("test@testuser.com")
				.build();

		MercadoPagoOrderRequest.PaymentMethod paymentMethod = MercadoPagoOrderRequest.PaymentMethod.builder()
				.id("pix")
				.type("bank_transfer")
				.build();

		MercadoPagoOrderRequest.Payment payment = MercadoPagoOrderRequest.Payment.builder()
				.amount("100.00")
				.paymentMethod(paymentMethod)
				.expirationTime("PT30M")
				.build();

		MercadoPagoOrderRequest.Transactions transactions = MercadoPagoOrderRequest.Transactions.builder()
				.payments(List.of(payment))
				.build();

		MercadoPagoOrderRequest request = MercadoPagoOrderRequest.builder()
				.type("online")
				.totalAmount("100.00")
				.externalReference("pedido_123")
				.processingMode("automatic")
				.transactions(transactions)
				.payer(payer)
				.build();

		// Act
		String json = objectMapper.writeValueAsString(request);

		// Assert
		assertNotNull(json);
		assertTrue(json.contains("\"type\":\"online\""));
		assertTrue(json.contains("\"total_amount\":\"100.00\""));
		assertTrue(json.contains("\"external_reference\":\"pedido_123\""));
		assertTrue(json.contains("\"processing_mode\":\"automatic\""));
		assertTrue(json.contains("\"first_name\":\"APRO\""));
		assertTrue(json.contains("\"payment_method\""));
		assertTrue(json.contains("\"expiration_time\":\"PT30M\""));
	}

	/**
	 * Testa deserialização completa do objeto
	 */
	@Test
	void testCompleteJsonDeserialization() throws JsonProcessingException {
		// Arrange
		String json = "{" +
				"\"type\":\"online\"," +
				"\"total_amount\":\"100.00\"," +
				"\"external_reference\":\"pedido_123\"," +
				"\"processing_mode\":\"automatic\"," +
				"\"transactions\":{" +
				"\"payments\":[{" +
				"\"amount\":\"100.00\"," +
				"\"payment_method\":{\"id\":\"pix\",\"type\":\"bank_transfer\"}," +
				"\"expiration_time\":\"PT30M\"" +
				"}]" +
				"}," +
				"\"payer\":{\"first_name\":\"APRO\",\"email\":\"test@testuser.com\"}" +
				"}";

		// Act
		MercadoPagoOrderRequest request = objectMapper.readValue(json, MercadoPagoOrderRequest.class);

		// Assert
		assertNotNull(request);
		assertEquals("online", request.getType());
		assertEquals("100.00", request.getTotalAmount());
		assertEquals("pedido_123", request.getExternalReference());
		assertEquals("automatic", request.getProcessingMode());
		assertNotNull(request.getTransactions());
		assertNotNull(request.getTransactions().getPayments());
		assertEquals(1, request.getTransactions().getPayments().size());
		assertEquals("pix", request.getTransactions().getPayments().get(0).getPaymentMethod().getId());
		assertEquals("APRO", request.getPayer().getFirstName());
	}

	/**
	 * Testa objeto vazio (NoArgsConstructor)
	 */
	@Test
	void testNoArgsConstructor() {
		// Act
		MercadoPagoOrderRequest request = new MercadoPagoOrderRequest();

		// Assert
		assertNotNull(request);
		assertNull(request.getType());
		assertNull(request.getTotalAmount());
		assertNull(request.getExternalReference());
		assertNull(request.getProcessingMode());
		assertNull(request.getTransactions());
		assertNull(request.getPayer());
	}

	/**
	 * Testa múltiplos payments
	 */
	@Test
	void testMultiplePayments() {
		// Arrange
		MercadoPagoOrderRequest.Payment payment1 = MercadoPagoOrderRequest.Payment.builder()
				.amount("50.00")
				.build();

		MercadoPagoOrderRequest.Payment payment2 = MercadoPagoOrderRequest.Payment.builder()
				.amount("50.00")
				.build();

		MercadoPagoOrderRequest.Transactions transactions = MercadoPagoOrderRequest.Transactions.builder()
				.payments(List.of(payment1, payment2))
				.build();

		// Act
		MercadoPagoOrderRequest request = MercadoPagoOrderRequest.builder()
				.transactions(transactions)
				.build();

		// Assert
		assertNotNull(request.getTransactions());
		assertEquals(2, request.getTransactions().getPayments().size());
		assertEquals("50.00", request.getTransactions().getPayments().get(0).getAmount());
		assertEquals("50.00", request.getTransactions().getPayments().get(1).getAmount());
	}

	/**
	 * Testa modificação de campos via setters
	 */
	@Test
	void testSetters() {
		// Arrange
		MercadoPagoOrderRequest request = new MercadoPagoOrderRequest();

		// Act
		request.setType("online");
		request.setTotalAmount("200.00");
		request.setExternalReference("pedido_456");
		request.setProcessingMode("manual");

		// Assert
		assertEquals("online", request.getType());
		assertEquals("200.00", request.getTotalAmount());
		assertEquals("pedido_456", request.getExternalReference());
		assertEquals("manual", request.getProcessingMode());
	}

	/**
	 * Testa anotação @JsonProperty em campos
	 */
	@Test
	void testJsonPropertyAnnotations() throws NoSuchFieldException {
		// Assert - totalAmount
		assertTrue(MercadoPagoOrderRequest.class.getDeclaredField("totalAmount")
				.isAnnotationPresent(JsonProperty.class));
		assertEquals("total_amount",
				MercadoPagoOrderRequest.class.getDeclaredField("totalAmount")
						.getAnnotation(JsonProperty.class).value());

		// Assert - externalReference
		assertTrue(MercadoPagoOrderRequest.class.getDeclaredField("externalReference")
				.isAnnotationPresent(JsonProperty.class));
		assertEquals("external_reference",
				MercadoPagoOrderRequest.class.getDeclaredField("externalReference")
						.getAnnotation(JsonProperty.class).value());

		// Assert - processingMode
		assertTrue(MercadoPagoOrderRequest.class.getDeclaredField("processingMode")
				.isAnnotationPresent(JsonProperty.class));
		assertEquals("processing_mode",
				MercadoPagoOrderRequest.class.getDeclaredField("processingMode")
						.getAnnotation(JsonProperty.class).value());
	}

	/**
	 * Testa equals e hashCode (Lombok @Data)
	 */
	@Test
	void testEqualsAndHashCode() {
		// Arrange
		MercadoPagoOrderRequest request1 = MercadoPagoOrderRequest.builder()
				.type("online")
				.totalAmount("100.00")
				.build();

		MercadoPagoOrderRequest request2 = MercadoPagoOrderRequest.builder()
				.type("online")
				.totalAmount("100.00")
				.build();

		// Assert
		assertEquals(request1, request2);
		assertEquals(request1.hashCode(), request2.hashCode());
	}

	/**
	 * Testa toString (Lombok @Data)
	 */
	@Test
	void testToString() {
		// Arrange
		MercadoPagoOrderRequest request = MercadoPagoOrderRequest.builder()
				.type("online")
				.totalAmount("100.00")
				.externalReference("pedido_123")
				.build();

		// Act
		String toString = request.toString();

		// Assert
		assertNotNull(toString);
		assertTrue(toString.contains("type=online"));
		assertTrue(toString.contains("totalAmount=100.00"));
		assertTrue(toString.contains("externalReference=pedido_123"));
	}
}

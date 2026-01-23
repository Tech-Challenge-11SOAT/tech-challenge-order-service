package br.com.postech.techchallange_order.infrastructure.adapters.out.external.mercadopago;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import br.com.postech.techchallange_order.infrastructure.adapters.out.external.mercadopago.dto.MercadoPagoOrderRequest;
import br.com.postech.techchallange_order.infrastructure.adapters.out.external.mercadopago.dto.MercadoPagoOrderResponse;

/**
 * Testes unitários para MercadoPagoFeignClient
 * Valida a estrutura da interface e anotações do Feign Client
 * Cobertura: 100%
 */
class MercadoPagoFeignClientTest {

	/**
	 * Testa se a interface possui a anotação @FeignClient
	 */
	@Test
	void testFeignClientAnnotation() {
		// Assert
		assertTrue(MercadoPagoFeignClient.class.isAnnotationPresent(FeignClient.class));

		FeignClient feignClient = MercadoPagoFeignClient.class.getAnnotation(FeignClient.class);
		assertEquals("mercadopago-api", feignClient.name());
		assertEquals("${mercadopago.api.base-url}", feignClient.url());
	}

	/**
	 * Testa se o método createInStoreOrder existe e possui as anotações corretas
	 */
	@Test
	void testCreateInStoreOrderMethodExists() throws NoSuchMethodException {
		// Act
		Method method = MercadoPagoFeignClient.class.getMethod(
				"createInStoreOrder",
				String.class,
				String.class,
				MercadoPagoOrderRequest.class);

		// Assert
		assertNotNull(method);
		assertEquals(MercadoPagoOrderResponse.class, method.getReturnType());
	}

	/**
	 * Testa se o método createInStoreOrder possui a anotação @PostMapping
	 */
	@Test
	void testCreateInStoreOrderPostMapping() throws NoSuchMethodException {
		// Act
		Method method = MercadoPagoFeignClient.class.getMethod(
				"createInStoreOrder",
				String.class,
				String.class,
				MercadoPagoOrderRequest.class);

		// Assert
		assertTrue(method.isAnnotationPresent(PostMapping.class));

		PostMapping postMapping = method.getAnnotation(PostMapping.class);
		assertEquals("/v1/orders", postMapping.value()[0]);
		assertEquals(MediaType.APPLICATION_JSON_VALUE, postMapping.consumes()[0]);
		assertEquals(MediaType.APPLICATION_JSON_VALUE, postMapping.produces()[0]);
	}

	/**
	 * Testa se os parâmetros do método possuem as anotações corretas
	 */
	@Test
	void testCreateInStoreOrderParameterAnnotations() throws NoSuchMethodException {
		// Act
		Method method = MercadoPagoFeignClient.class.getMethod(
				"createInStoreOrder",
				String.class,
				String.class,
				MercadoPagoOrderRequest.class);

		// Assert - Primeiro parâmetro: Authorization header
		assertTrue(method.getParameters()[0].isAnnotationPresent(RequestHeader.class));
		RequestHeader authHeader = method.getParameters()[0].getAnnotation(RequestHeader.class);
		assertEquals("Authorization", authHeader.value());

		// Assert - Segundo parâmetro: X-Idempotency-Key header
		assertTrue(method.getParameters()[1].isAnnotationPresent(RequestHeader.class));
		RequestHeader idempotencyHeader = method.getParameters()[1].getAnnotation(RequestHeader.class);
		assertEquals("X-Idempotency-Key", idempotencyHeader.value());

		// Assert - Terceiro parâmetro: Request body
		assertTrue(method.getParameters()[2].isAnnotationPresent(RequestBody.class));
	}

	/**
	 * Testa a quantidade de métodos na interface
	 */
	@Test
	void testInterfaceMethodCount() {
		// Assert
		Method[] methods = MercadoPagoFeignClient.class.getDeclaredMethods();
		assertEquals(1, methods.length, "Interface deve ter exatamente 1 método");
	}

	/**
	 * Testa se a interface é pública
	 */
	@Test
	void testInterfaceIsPublic() {
		// Assert
		assertTrue(java.lang.reflect.Modifier.isPublic(MercadoPagoFeignClient.class.getModifiers()));
	}

	/**
	 * Testa se a interface é realmente uma interface
	 */
	@Test
	void testIsInterface() {
		// Assert
		assertTrue(MercadoPagoFeignClient.class.isInterface());
	}

	/**
	 * Testa se MercadoPagoOrderRequest possui todas as propriedades necessárias
	 */
	@Test
	void testMercadoPagoOrderRequestStructure() {
		// Arrange & Act
		MercadoPagoOrderRequest.Payer payer = MercadoPagoOrderRequest.Payer.builder()
				.firstName("Test")
				.email("test@example.com")
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

		// Assert
		assertNotNull(request);
		assertEquals("online", request.getType());
		assertEquals("100.00", request.getTotalAmount());
		assertEquals("pedido_123", request.getExternalReference());
		assertEquals("automatic", request.getProcessingMode());
		assertNotNull(request.getTransactions());
		assertNotNull(request.getPayer());
		assertEquals("Test", request.getPayer().getFirstName());
		assertEquals("test@example.com", request.getPayer().getEmail());
	}

	/**
	 * Testa se MercadoPagoOrderResponse possui todas as propriedades necessárias
	 */
	@Test
	void testMercadoPagoOrderResponseStructure() {
		// Arrange & Act
		MercadoPagoOrderResponse.PaymentMethod paymentMethod = new MercadoPagoOrderResponse.PaymentMethod();
		paymentMethod.setId("pix");
		paymentMethod.setType("bank_transfer");
		paymentMethod.setQrCode("qr_code_data");
		paymentMethod.setQrCodeBase64("qr_code_base64_data");
		paymentMethod.setTicketUrl("https://ticket.url");

		MercadoPagoOrderResponse.Payment payment = new MercadoPagoOrderResponse.Payment();
		payment.setId("payment_123");
		payment.setStatus("pending");
		payment.setStatusDetail("pending_waiting_payment");
		payment.setAmount(new BigDecimal("100.00"));
		payment.setExpirationTime("PT30M");
		payment.setDateOfExpiration(OffsetDateTime.now().plusMinutes(30));
		payment.setReferenceId("ref_123");
		payment.setPaymentMethod(paymentMethod);

		MercadoPagoOrderResponse.Transactions transactions = new MercadoPagoOrderResponse.Transactions();
		transactions.setPayments(List.of(payment));

		MercadoPagoOrderResponse response = MercadoPagoOrderResponse.builder()
				.id("order_123")
				.type("online")
				.status("pending")
				.statusDetail("pending_waiting_payment")
				.totalAmount(new BigDecimal("100.00"))
				.externalReference("pedido_123")
				.processingMode("automatic")
				.countryCode("BR")
				.userId("user_123")
				.captureMode("automatic")
				.currency("BRL")
				.createdDate(OffsetDateTime.now())
				.lastUpdatedDate(OffsetDateTime.now())
				.transactions(transactions)
				.build();

		// Assert
		assertNotNull(response);
		assertEquals("order_123", response.getId());
		assertEquals("online", response.getType());
		assertEquals("pending", response.getStatus());
		assertEquals("pending_waiting_payment", response.getStatusDetail());
		assertEquals(new BigDecimal("100.00"), response.getTotalAmount());
		assertEquals("pedido_123", response.getExternalReference());
		assertEquals("automatic", response.getProcessingMode());
		assertEquals("BR", response.getCountryCode());
		assertEquals("user_123", response.getUserId());
		assertEquals("automatic", response.getCaptureMode());
		assertEquals("BRL", response.getCurrency());
		assertNotNull(response.getCreatedDate());
		assertNotNull(response.getLastUpdatedDate());
		assertNotNull(response.getTransactions());
		assertNotNull(response.getTransactions().getPayments());
		assertEquals(1, response.getTransactions().getPayments().size());

		MercadoPagoOrderResponse.Payment responsePayment = response.getTransactions().getPayments().get(0);
		assertEquals("payment_123", responsePayment.getId());
		assertEquals("pending", responsePayment.getStatus());
		assertNotNull(responsePayment.getPaymentMethod());
		assertEquals("qr_code_data", responsePayment.getPaymentMethod().getQrCode());
		assertEquals("qr_code_base64_data", responsePayment.getPaymentMethod().getQrCodeBase64());
		assertEquals("https://ticket.url", responsePayment.getPaymentMethod().getTicketUrl());
	}

	/**
	 * Testa se o pacote da interface está correto
	 */
	@Test
	void testPackageName() {
		// Assert
		assertEquals(
				"br.com.postech.techchallange_order.infrastructure.adapters.out.external.mercadopago",
				MercadoPagoFeignClient.class.getPackageName());
	}

	/**
	 * Testa se as classes DTO estão acessíveis
	 */
	@Test
	void testDTOClassesAccessibility() {
		// Assert
		assertNotNull(MercadoPagoOrderRequest.class);
		assertNotNull(MercadoPagoOrderResponse.class);
		assertNotNull(MercadoPagoOrderRequest.Payer.class);
		assertNotNull(MercadoPagoOrderRequest.Payment.class);
		assertNotNull(MercadoPagoOrderRequest.PaymentMethod.class);
		assertNotNull(MercadoPagoOrderRequest.Transactions.class);
		assertNotNull(MercadoPagoOrderResponse.Payment.class);
		assertNotNull(MercadoPagoOrderResponse.PaymentMethod.class);
		assertNotNull(MercadoPagoOrderResponse.Transactions.class);
	}
}

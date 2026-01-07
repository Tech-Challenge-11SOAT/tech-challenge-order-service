package br.com.postech.techchallange_order.infrastructure.adapters.out.external.mercadopago;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import br.com.postech.techchallange_order.domain.model.Order;
import br.com.postech.techchallange_order.infrastructure.adapters.out.external.mercadopago.dto.MercadoPagoOrderRequest;
import br.com.postech.techchallange_order.infrastructure.adapters.out.external.mercadopago.dto.MercadoPagoOrderResponse;
import feign.FeignException;

/**
 * Testes unitários para MercadoPagoAdapter
 * Cobertura: 100%
 */
@ExtendWith(MockitoExtension.class)
class MercadoPagoAdapterTest {

	@Mock
	private MercadoPagoFeignClient mercadoPagoFeignClient;

	@InjectMocks
	private MercadoPagoAdapter mercadoPagoAdapter;

	private static final String ACCESS_TOKEN = "TEST_ACCESS_TOKEN";
	private static final String USER_ID = "TEST_USER_ID";
	private static final String POS_ID = "SUC001POS001";
	private static final String PAYER_EMAIL = "test@example.com";

	@BeforeEach
	void setUp() {
		ReflectionTestUtils.setField(mercadoPagoAdapter, "accessToken", ACCESS_TOKEN);
		ReflectionTestUtils.setField(mercadoPagoAdapter, "userId", USER_ID);
		ReflectionTestUtils.setField(mercadoPagoAdapter, "externalPosId", POS_ID);
		ReflectionTestUtils.setField(mercadoPagoAdapter, "integrationActive", true);
	}

	/**
	 * Testa a criação de pedido com sucesso quando a integração está ativa
	 */
	@Test
	void testCreatePaymentOrder_Success() {
		// Arrange
		Order order = createTestOrder();
		MercadoPagoOrderResponse response = createTestResponse();

		when(mercadoPagoFeignClient.createInStoreOrder(
				eq("Bearer " + ACCESS_TOKEN),
				eq(USER_ID),
				eq(POS_ID),
				any(MercadoPagoOrderRequest.class)))
				.thenReturn(response);

		// Act
		Order result = mercadoPagoAdapter.createPaymentOrder(order, PAYER_EMAIL);

		// Assert
		assertNotNull(result);
		assertNotNull(result.getPayment());
		assertNotNull(result.getPayment().getMercadoPagoInfo());
		assertEquals("MP_ORDER_123", result.getPayment().getMercadoPagoInfo().getOrderId());
		assertEquals("QR_CODE_DATA", result.getPayment().getMercadoPagoInfo().getQrCode());
		assertEquals(order.getId(), result.getPayment().getMercadoPagoInfo().getExternalReference());
		assertEquals("pending", result.getPayment().getMercadoPagoInfo().getStatus());

		verify(mercadoPagoFeignClient, times(1)).createInStoreOrder(
				eq("Bearer " + ACCESS_TOKEN),
				eq(USER_ID),
				eq(POS_ID),
				any(MercadoPagoOrderRequest.class));
	}

	/**
	 * Testa quando a integração está desabilitada
	 */
	@Test
	void testCreatePaymentOrder_IntegrationDisabled() {
		// Arrange
		ReflectionTestUtils.setField(mercadoPagoAdapter, "integrationActive", false);
		Order order = createTestOrder();

		// Act
		Order result = mercadoPagoAdapter.createPaymentOrder(order, PAYER_EMAIL);

		// Assert
		assertNotNull(result);
		assertEquals(order, result);
		verify(mercadoPagoFeignClient, never()).createInStoreOrder(anyString(), anyString(), anyString(), any());
	}

	/**
	 * Testa quando o response do MercadoPago é nulo
	 */
	@Test
	void testCreatePaymentOrder_NullResponse() {
		// Arrange
		Order order = createTestOrder();

		when(mercadoPagoFeignClient.createInStoreOrder(
				anyString(),
				anyString(),
				anyString(),
				any(MercadoPagoOrderRequest.class)))
				.thenReturn(null);

		// Act
		Order result = mercadoPagoAdapter.createPaymentOrder(order, PAYER_EMAIL);

		// Assert
		assertNotNull(result);
		assertEquals(order, result);
		verify(mercadoPagoFeignClient, times(1)).createInStoreOrder(anyString(), anyString(), anyString(), any());
	}

	/**
	 * Testa quando ocorre FeignException
	 */
	@Test
	void testCreatePaymentOrder_FeignException() {
		// Arrange
		Order order = createTestOrder();
		FeignException feignException = mock(FeignException.class);
		when(feignException.getMessage()).thenReturn("Error connecting to MercadoPago");

		when(mercadoPagoFeignClient.createInStoreOrder(
				anyString(),
				anyString(),
				anyString(),
				any(MercadoPagoOrderRequest.class)))
				.thenThrow(feignException);

		// Act
		Order result = mercadoPagoAdapter.createPaymentOrder(order, PAYER_EMAIL);

		// Assert
		assertNotNull(result);
		assertEquals(order, result);
		verify(mercadoPagoFeignClient, times(1)).createInStoreOrder(anyString(), anyString(), anyString(), any());
	}

	/**
	 * Testa quando ocorre uma exceção genérica
	 */
	@Test
	void testCreatePaymentOrder_GenericException() {
		// Arrange
		Order order = createTestOrder();

		when(mercadoPagoFeignClient.createInStoreOrder(
				anyString(),
				anyString(),
				anyString(),
				any(MercadoPagoOrderRequest.class)))
				.thenThrow(new RuntimeException("Unexpected error"));

		// Act
		Order result = mercadoPagoAdapter.createPaymentOrder(order, PAYER_EMAIL);

		// Assert
		assertNotNull(result);
		assertEquals(order, result);
		verify(mercadoPagoFeignClient, times(1)).createInStoreOrder(anyString(), anyString(), anyString(), any());
	}

	/**
	 * Testa criação de pedido quando payment é nulo - captura NullPointerException
	 */
	@Test
	void testCreatePaymentOrder_NullPayment() {
		// Arrange
		Order order = createTestOrder();
		order.setPayment(null);

		// Act
		Order result = mercadoPagoAdapter.createPaymentOrder(order, PAYER_EMAIL);

		// Assert
		assertNotNull(result);
		assertNull(result.getPayment());
		// Verifica que não houve chamada ao client devido à exceção no buildRequest
		verify(mercadoPagoFeignClient, never()).createInStoreOrder(anyString(), anyString(), anyString(), any());
	}

	/**
	 * Testa quando response não é nulo mas payment do order é nulo após a chamada
	 * Isso testa o branch: if (order.getPayment() != null)
	 */
	@Test
	void testCreatePaymentOrder_ResponseNotNullButPaymentIsNull() {
		// Arrange
		Order order = createTestOrder();
		MercadoPagoOrderResponse response = createTestResponse();

		when(mercadoPagoFeignClient.createInStoreOrder(
				anyString(),
				anyString(),
				anyString(),
				any(MercadoPagoOrderRequest.class)))
				.thenAnswer(invocation -> {
					// Simula o payment sendo setado como null durante a chamada
					order.setPayment(null);
					return response;
				});

		// Act
		Order result = mercadoPagoAdapter.createPaymentOrder(order, PAYER_EMAIL);

		// Assert
		assertNotNull(result);
		assertNull(result.getPayment());
		verify(mercadoPagoFeignClient, times(1)).createInStoreOrder(anyString(), anyString(), anyString(), any());
	}

	/**
	 * Testa criação de pedido com múltiplos itens
	 */
	@Test
	void testCreatePaymentOrder_MultipleItems() {
		// Arrange
		Order order = createTestOrderWithMultipleItems();
		MercadoPagoOrderResponse response = createTestResponse();

		when(mercadoPagoFeignClient.createInStoreOrder(
				anyString(),
				anyString(),
				anyString(),
				any(MercadoPagoOrderRequest.class)))
				.thenReturn(response);

		// Act
		Order result = mercadoPagoAdapter.createPaymentOrder(order, PAYER_EMAIL);

		// Assert
		assertNotNull(result);
		assertNotNull(result.getPayment().getMercadoPagoInfo());
		assertEquals("MP_ORDER_123", result.getPayment().getMercadoPagoInfo().getOrderId());
		verify(mercadoPagoFeignClient, times(1)).createInStoreOrder(anyString(), anyString(), anyString(), any());
	}

	/**
	 * Testa criação de pedido com itens vazios
	 */
	@Test
	void testCreatePaymentOrder_EmptyItems() {
		// Arrange
		Order order = createTestOrder();
		order.setItems(new ArrayList<>());
		MercadoPagoOrderResponse response = createTestResponse();

		when(mercadoPagoFeignClient.createInStoreOrder(
				anyString(),
				anyString(),
				anyString(),
				any(MercadoPagoOrderRequest.class)))
				.thenReturn(response);

		// Act
		Order result = mercadoPagoAdapter.createPaymentOrder(order, PAYER_EMAIL);

		// Assert
		assertNotNull(result);
		assertNotNull(result.getPayment().getMercadoPagoInfo());
		verify(mercadoPagoFeignClient, times(1)).createInStoreOrder(anyString(), anyString(), anyString(), any());
	}

	/**
	 * Testa a construção da requisição MercadoPago
	 */
	@Test
	void testBuildRequest_VerifyStructure() {
		// Arrange
		Order order = createTestOrderWithMultipleItems();

		// Act
		Order result = mercadoPagoAdapter.createPaymentOrder(order, PAYER_EMAIL);

		// Assert
		verify(mercadoPagoFeignClient, times(1)).createInStoreOrder(
				eq("Bearer " + ACCESS_TOKEN),
				eq(USER_ID),
				eq(POS_ID),
				argThat(request -> {
					assertNotNull(request);
					assertEquals(order.getId(), request.getExternalReference());
					assertEquals("Pedido " + order.getId(), request.getTitle());
					assertEquals("Pedido realizado na lanchonete", request.getDescription());
					assertEquals(order.getPayment().getTotalAmount(), request.getTotalAmount());
					assertEquals(PAYER_EMAIL, request.getPayer().getEmail());
					assertEquals(3, request.getItems().size());
					return true;
				}));
	}

	/**
	 * Testa a construção de itens na requisição
	 */
	@Test
	void testBuildRequest_ItemsMapping() {
		// Arrange
		Order order = createTestOrder();
		MercadoPagoOrderResponse response = createTestResponse();

		when(mercadoPagoFeignClient.createInStoreOrder(
				anyString(),
				anyString(),
				anyString(),
				any(MercadoPagoOrderRequest.class)))
				.thenReturn(response);

		// Act
		mercadoPagoAdapter.createPaymentOrder(order, PAYER_EMAIL);

		// Assert
		verify(mercadoPagoFeignClient).createInStoreOrder(
				anyString(),
				anyString(),
				anyString(),
				argThat(request -> {
					MercadoPagoOrderRequest.Item item = request.getItems().get(0);
					assertEquals("1", item.getSkuNumber());
					assertEquals("food", item.getCategory());
					assertEquals("Produto 1", item.getTitle());
					assertEquals("Produto", item.getDescription());
					assertEquals(new BigDecimal("10.00"), item.getUnitPrice());
					assertEquals(2, item.getQuantity());
					assertEquals("unit", item.getUnitMeasure());
					assertEquals(new BigDecimal("20.00"), item.getTotalAmount());
					return true;
				}));
	}

	/**
	 * Testa o método getPaymentStatus (ainda não implementado)
	 */
	@Test
	void testGetPaymentStatus_NotImplemented() {
		// Act
		Order result = mercadoPagoAdapter.getPaymentStatus("ORDER_123");

		// Assert
		assertNull(result);
	}

	/**
	 * Testa integração ativa com valor true
	 */
	@Test
	void testCreatePaymentOrder_IntegrationActiveTrueExplicit() {
		// Arrange
		ReflectionTestUtils.setField(mercadoPagoAdapter, "integrationActive", Boolean.TRUE);
		Order order = createTestOrder();
		MercadoPagoOrderResponse response = createTestResponse();

		when(mercadoPagoFeignClient.createInStoreOrder(anyString(), anyString(), anyString(), any()))
				.thenReturn(response);

		// Act
		Order result = mercadoPagoAdapter.createPaymentOrder(order, PAYER_EMAIL);

		// Assert
		assertNotNull(result);
		assertNotNull(result.getPayment().getMercadoPagoInfo());
		verify(mercadoPagoFeignClient, times(1)).createInStoreOrder(anyString(), anyString(), anyString(), any());
	}

	/**
	 * Testa com valores diferentes de configuração
	 */
	@Test
	void testCreatePaymentOrder_DifferentConfigurations() {
		// Arrange
		ReflectionTestUtils.setField(mercadoPagoAdapter, "accessToken", "CUSTOM_TOKEN");
		ReflectionTestUtils.setField(mercadoPagoAdapter, "userId", "CUSTOM_USER");
		ReflectionTestUtils.setField(mercadoPagoAdapter, "externalPosId", "CUSTOM_POS");

		Order order = createTestOrder();
		MercadoPagoOrderResponse response = createTestResponse();

		when(mercadoPagoFeignClient.createInStoreOrder(
				eq("Bearer CUSTOM_TOKEN"),
				eq("CUSTOM_USER"),
				eq("CUSTOM_POS"),
				any(MercadoPagoOrderRequest.class)))
				.thenReturn(response);

		// Act
		Order result = mercadoPagoAdapter.createPaymentOrder(order, PAYER_EMAIL);

		// Assert
		assertNotNull(result);
		verify(mercadoPagoFeignClient).createInStoreOrder(
				eq("Bearer CUSTOM_TOKEN"),
				eq("CUSTOM_USER"),
				eq("CUSTOM_POS"),
				any(MercadoPagoOrderRequest.class));
	}

	/**
	 * Testa com email do pagador diferente
	 */
	@Test
	void testCreatePaymentOrder_DifferentPayerEmail() {
		// Arrange
		String customEmail = "custom@email.com";
		Order order = createTestOrder();
		MercadoPagoOrderResponse response = createTestResponse();

		when(mercadoPagoFeignClient.createInStoreOrder(anyString(), anyString(), anyString(), any()))
				.thenReturn(response);

		// Act
		mercadoPagoAdapter.createPaymentOrder(order, customEmail);

		// Assert
		verify(mercadoPagoFeignClient).createInStoreOrder(
				anyString(),
				anyString(),
				anyString(),
				argThat(request -> customEmail.equals(request.getPayer().getEmail())));
	}

	// ========== Métodos auxiliares ==========

	private Order createTestOrder() {
		Order order = new Order();
		order.setId("ORDER_123");
		order.setOrderId(123L);
		order.setCustomerId("CUSTOMER_456");
		order.setOrderDate(Instant.now());
		order.setCreatedAt(Instant.now());
		order.setUpdatedAt(Instant.now());

		List<Order.Item> items = new ArrayList<>();
		Order.Item item = new Order.Item();
		item.setProductId(1L);
		item.setQuantity(2);
		item.setUnitPrice(new BigDecimal("10.00"));
		item.setSubtotal(new BigDecimal("20.00"));
		items.add(item);
		order.setItems(items);

		Order.Payment payment = new Order.Payment();
		payment.setPaymentId(1L);
		payment.setTotalAmount(new BigDecimal("20.00"));
		payment.setPaymentMethod("QR_CODE");
		payment.setPaymentDate(Instant.now());
		order.setPayment(payment);

		return order;
	}

	private Order createTestOrderWithMultipleItems() {
		Order order = createTestOrder();

		Order.Item item2 = new Order.Item();
		item2.setProductId(2L);
		item2.setQuantity(1);
		item2.setUnitPrice(new BigDecimal("15.00"));
		item2.setSubtotal(new BigDecimal("15.00"));

		Order.Item item3 = new Order.Item();
		item3.setProductId(3L);
		item3.setQuantity(3);
		item3.setUnitPrice(new BigDecimal("5.00"));
		item3.setSubtotal(new BigDecimal("15.00"));

		order.getItems().add(item2);
		order.getItems().add(item3);

		order.getPayment().setTotalAmount(new BigDecimal("50.00"));

		return order;
	}

	private MercadoPagoOrderResponse createTestResponse() {
		return MercadoPagoOrderResponse.builder()
				.inStoreOrderId("MP_ORDER_123")
				.qrData("QR_CODE_DATA")
				.build();
	}
}

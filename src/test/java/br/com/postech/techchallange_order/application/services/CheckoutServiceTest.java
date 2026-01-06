package br.com.postech.techchallange_order.application.services;

import br.com.postech.techchallange_order.domain.enums.StatusPagamentoEnum;
import br.com.postech.techchallange_order.domain.enums.StatusPedidoEnum;
import br.com.postech.techchallange_order.domain.model.Order;
import br.com.postech.techchallange_order.domain.model.PaymentTransaction;
import br.com.postech.techchallange_order.domain.ports.in.OrderStatusHistoryUseCase;
import br.com.postech.techchallange_order.domain.ports.in.OrderUseCase;
import br.com.postech.techchallange_order.domain.ports.in.PaymentTransactionUseCase;
import br.com.postech.techchallange_order.domain.ports.out.OrderRepositoryPort;
import br.com.postech.techchallange_order.infrastructure.adapters.in.rest.dto.CheckoutRequest;
import br.com.postech.techchallange_order.infrastructure.adapters.in.rest.dto.response.CheckoutResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CheckoutServiceTest {

	@Mock
	private OrderUseCase orderUseCase;

	@Mock
	private OrderStatusHistoryUseCase orderStatusHistoryUseCase;

	@Mock
	private PaymentTransactionUseCase paymentTransactionUseCase;

	@Mock
	private MercadoPagoService mercadoPagoService;

	@Mock
	private OrderRepositoryPort orderRepository;

	@InjectMocks
	private CheckoutService checkoutService;

	private CheckoutRequest checkoutRequest;
	private Order order;
	private Order updatedOrder;

	@BeforeEach
	void setUp() {
		checkoutRequest = new CheckoutRequest();
		checkoutRequest.setIdCliente("customer-123");
		checkoutRequest.setMetodoPagamento("PIX");

		CheckoutRequest.ItemProduto item = new CheckoutRequest.ItemProduto();
		item.setIdProduto(1L);
		item.setQuantidade(2);
		item.setPrecoUnitario(BigDecimal.valueOf(50));
		checkoutRequest.setProdutos(Arrays.asList(item));

		order = new Order();
		order.setId("order-123");
		order.setCustomerId("customer-123");

		Order.Payment payment = new Order.Payment();
		payment.setPaymentId(100L);
		payment.setTotalAmount(BigDecimal.valueOf(100));
		payment.setPaymentMethod("PIX");

		Order.Payment.PaymentStatus paymentStatus = new Order.Payment.PaymentStatus();
		paymentStatus.setId(1L);
		paymentStatus.setName(StatusPagamentoEnum.PENDENTE.getStatus());
		payment.setStatus(paymentStatus);

		order.setPayment(payment);

		Order.Status status = new Order.Status();
		status.setId(1L);
		status.setName(StatusPedidoEnum.RECEBIDO.getStatus());
		order.setStatus(status);

		updatedOrder = new Order();
		updatedOrder.setId("order-123");
		updatedOrder.setCustomerId("customer-123");
		updatedOrder.setPayment(payment);
		updatedOrder.setStatus(status);

		Order.MercadoPagoInfo mpInfo = new Order.MercadoPagoInfo();
		mpInfo.setQrCode("qr-code-data");
		mpInfo.setQrCodeBase64("qr-base64-data");
		updatedOrder.getPayment().setMercadoPagoInfo(mpInfo);
	}

	@Test
	void shouldProcessCheckoutSuccessfully() {
		when(orderUseCase.createOrder(any(CheckoutRequest.class))).thenReturn(order);
		when(mercadoPagoService.processPaymentWithMercadoPago(any(Order.class))).thenReturn(updatedOrder);
		when(orderRepository.save(any(Order.class))).thenReturn(updatedOrder);

		CheckoutResponse response = checkoutService.processarCheckout(checkoutRequest);

		assertNotNull(response);
		assertEquals("order-123", response.getIdPedido());
		assertEquals(StatusPedidoEnum.RECEBIDO.getStatus(), response.getStatus());
		assertEquals("qr-code-data", response.getQrCode());
		assertEquals("qr-base64-data", response.getQrCodeBase64());

		verify(orderUseCase, times(1)).createOrder(checkoutRequest);
		verify(paymentTransactionUseCase, times(1)).createPaymentTransaction(any(PaymentTransaction.class));
		verify(orderStatusHistoryUseCase, times(1)).recordStatusChange(eq(order), isNull());
		verify(mercadoPagoService, times(1)).processPaymentWithMercadoPago(order);
		verify(orderRepository, times(1)).save(updatedOrder);
	}

	@Test
	void shouldProcessCheckoutWithoutMercadoPagoInfo() {
		Order orderWithoutMpInfo = new Order();
		orderWithoutMpInfo.setId("order-456");
		orderWithoutMpInfo.setStatus(order.getStatus());
		orderWithoutMpInfo.setPayment(new Order.Payment());

		when(orderUseCase.createOrder(any(CheckoutRequest.class))).thenReturn(order);
		when(mercadoPagoService.processPaymentWithMercadoPago(any(Order.class))).thenReturn(orderWithoutMpInfo);
		when(orderRepository.save(any(Order.class))).thenReturn(orderWithoutMpInfo);

		CheckoutResponse response = checkoutService.processarCheckout(checkoutRequest);

		assertNotNull(response);
		assertEquals("order-456", response.getIdPedido());
		assertNull(response.getQrCode());
		assertNull(response.getQrCodeBase64());

		verify(orderUseCase, times(1)).createOrder(checkoutRequest);
		verify(mercadoPagoService, times(1)).processPaymentWithMercadoPago(order);
		verify(orderRepository, times(1)).save(orderWithoutMpInfo);
	}

	@Test
	void shouldProcessCheckoutWithNullPayment() {
		Order orderWithoutPayment = new Order();
		orderWithoutPayment.setId("order-789");
		orderWithoutPayment.setStatus(order.getStatus());
		orderWithoutPayment.setPayment(null);

		when(orderUseCase.createOrder(any(CheckoutRequest.class))).thenReturn(order);
		when(mercadoPagoService.processPaymentWithMercadoPago(any(Order.class))).thenReturn(orderWithoutPayment);
		when(orderRepository.save(any(Order.class))).thenReturn(orderWithoutPayment);

		CheckoutResponse response = checkoutService.processarCheckout(checkoutRequest);

		assertNotNull(response);
		assertEquals("order-789", response.getIdPedido());
		assertNull(response.getQrCode());
		assertNull(response.getQrCodeBase64());

		verify(orderUseCase, times(1)).createOrder(checkoutRequest);
		verify(mercadoPagoService, times(1)).processPaymentWithMercadoPago(order);
	}

	@Test
	void shouldCreatePaymentTransactionWithCorrectData() {
		when(orderUseCase.createOrder(any(CheckoutRequest.class))).thenReturn(order);
		when(mercadoPagoService.processPaymentWithMercadoPago(any(Order.class))).thenReturn(updatedOrder);
		when(orderRepository.save(any(Order.class))).thenReturn(updatedOrder);

		checkoutService.processarCheckout(checkoutRequest);

		verify(paymentTransactionUseCase, times(1)).createPaymentTransaction(argThat(transaction ->
			transaction.getOrderId().equals("order-123") &&
			transaction.getAmount().equals(BigDecimal.valueOf(100)) &&
			transaction.getPaymentMethod().equals("PIX") &&
			transaction.getStatus().equals(StatusPagamentoEnum.PENDENTE.getStatus()) &&
			transaction.getCreatedAt() != null
		));
	}

	@Test
	void shouldRecordOrderStatusHistory() {
		when(orderUseCase.createOrder(any(CheckoutRequest.class))).thenReturn(order);
		when(mercadoPagoService.processPaymentWithMercadoPago(any(Order.class))).thenReturn(updatedOrder);
		when(orderRepository.save(any(Order.class))).thenReturn(updatedOrder);

		checkoutService.processarCheckout(checkoutRequest);

		verify(orderStatusHistoryUseCase, times(1)).recordStatusChange(eq(order), isNull());
	}
}


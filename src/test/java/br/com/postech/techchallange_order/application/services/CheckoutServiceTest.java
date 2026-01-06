package br.com.postech.techchallange_order.application.services;

import br.com.postech.techchallange_order.domain.enums.StatusPagamentoEnum;
import br.com.postech.techchallange_order.domain.enums.StatusPedidoEnum;
import br.com.postech.techchallange_order.domain.model.Order;
import br.com.postech.techchallange_order.domain.model.PaymentTransaction;
import br.com.postech.techchallange_order.domain.ports.in.OrderStatusHistoryUseCase;
import br.com.postech.techchallange_order.domain.ports.in.OrderUseCase;
import br.com.postech.techchallange_order.domain.ports.in.PaymentTransactionUseCase;
import br.com.postech.techchallange_order.domain.ports.out.OrderRepositoryPort;
import br.com.postech.techchallange_order.helpers.OrderMother;
import br.com.postech.techchallange_order.infrastructure.adapters.in.rest.dto.CheckoutRequest;
import br.com.postech.techchallange_order.infrastructure.adapters.in.rest.dto.response.CheckoutResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;

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
		checkoutRequest = OrderMother.createCheckoutRequest();
		order = OrderMother.createCompleteOrder();

		updatedOrder = OrderMother.createOrderWithMercadoPago();
	}

	@Test
	void shouldProcessCheckoutSuccessfully() {
		when(orderUseCase.createOrder(any(CheckoutRequest.class))).thenReturn(order);
		when(mercadoPagoService.processPaymentWithMercadoPago(any(Order.class))).thenReturn(updatedOrder);
		when(orderRepository.save(any(Order.class))).thenReturn(updatedOrder);

		CheckoutResponse response = checkoutService.processarCheckout(checkoutRequest);

		assertNotNull(response);
		assertNotNull(response.getIdPedido());
		assertEquals(updatedOrder.getStatus().getName(), response.getStatus());
		assertEquals(updatedOrder.getPayment().getMercadoPagoInfo().getQrCode(), response.getQrCode());
		assertEquals(updatedOrder.getPayment().getMercadoPagoInfo().getQrCodeBase64(), response.getQrCodeBase64());

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


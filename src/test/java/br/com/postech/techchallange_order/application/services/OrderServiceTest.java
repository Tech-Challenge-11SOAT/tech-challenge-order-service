package br.com.postech.techchallange_order.application.services;

import br.com.postech.techchallange_order.domain.enums.StatusPagamentoEnum;
import br.com.postech.techchallange_order.domain.enums.StatusPedidoEnum;
import br.com.postech.techchallange_order.domain.model.Order;
import br.com.postech.techchallange_order.domain.ports.out.OrderRepositoryPort;
import br.com.postech.techchallange_order.helpers.OrderMother;
import br.com.postech.techchallange_order.infrastructure.adapters.in.rest.dto.CheckoutRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

	@Mock
	private OrderRepositoryPort orderRepository;

	@InjectMocks
	private OrderService orderService;

	private CheckoutRequest checkoutRequest;

	@BeforeEach
	void setUp() {
		checkoutRequest = OrderMother.createCheckoutRequest();
	}

	@Test
	void shouldCreateOrderSuccessfully() {
		Order savedOrder = new Order();
		savedOrder.setId("order-123");
		when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

		Order result = orderService.createOrder(checkoutRequest);

		assertNotNull(result);
		assertEquals("order-123", result.getId());

		verify(orderRepository, times(1)).save(any(Order.class));
	}

	@Test
	void shouldSetCustomerId() {
		Order savedOrder = new Order();
		when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

		orderService.createOrder(checkoutRequest);

		verify(orderRepository).save(argThat(order ->
			order.getCustomerId().equals("customer-123")
		));
	}

	@Test
	void shouldGenerateOrderId() {
		Order savedOrder = new Order();
		when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

		orderService.createOrder(checkoutRequest);

		verify(orderRepository).save(argThat(order ->
			order.getOrderId() != null && order.getOrderId() > 0
		));
	}

	@Test
	void shouldMapItemsCorrectly() {
		CheckoutRequest multiItemRequest = OrderMother.createCheckoutRequestWithMultipleItems();
		Order savedOrder = new Order();
		when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

		orderService.createOrder(multiItemRequest);

		verify(orderRepository).save(argThat(order ->
			order.getItems().size() == 2 &&
			order.getItems().get(0).getProductId() == 1L &&
			order.getItems().get(0).getQuantity() == 2 &&
			order.getItems().get(0).getUnitPrice().compareTo(BigDecimal.valueOf(50)) == 0 &&
			order.getItems().get(0).getSubtotal().compareTo(BigDecimal.valueOf(100)) == 0 &&
			order.getItems().get(1).getProductId() == 2L &&
			order.getItems().get(1).getQuantity() == 1 &&
			order.getItems().get(1).getUnitPrice().compareTo(BigDecimal.valueOf(100)) == 0 &&
			order.getItems().get(1).getSubtotal().compareTo(BigDecimal.valueOf(100)) == 0
		));
	}

	@Test
	void shouldCalculateTotalAmountCorrectly() {
		CheckoutRequest multiItemRequest = OrderMother.createCheckoutRequestWithMultipleItems();
		Order savedOrder = new Order();
		when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

		orderService.createOrder(multiItemRequest);

		verify(orderRepository).save(argThat(order ->
			order.getPayment().getTotalAmount().compareTo(BigDecimal.valueOf(200)) == 0
		));
	}

	@Test
	void shouldSetPaymentMethodCorrectly() {
		Order savedOrder = new Order();
		when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

		orderService.createOrder(checkoutRequest);

		verify(orderRepository).save(argThat(order ->
			order.getPayment().getPaymentMethod().equals("PIX")
		));
	}

	@Test
	void shouldSetInitialPaymentStatusAsPendente() {
		Order savedOrder = new Order();
		when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

		orderService.createOrder(checkoutRequest);

		verify(orderRepository).save(argThat(order ->
			order.getPayment().getStatus() != null &&
			order.getPayment().getStatus().getName().equals(StatusPagamentoEnum.PENDENTE.getStatus())
		));
	}

	@Test
	void shouldSetInitialOrderStatusAsRecebido() {
		Order savedOrder = new Order();
		when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

		orderService.createOrder(checkoutRequest);

		verify(orderRepository).save(argThat(order ->
			order.getStatus() != null &&
			order.getStatus().getName().equals(StatusPedidoEnum.RECEBIDO.getStatus())
		));
	}

	@Test
	void shouldSetCreatedAtAndUpdatedAt() {
		Order savedOrder = new Order();
		when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

		orderService.createOrder(checkoutRequest);

		verify(orderRepository).save(argThat(order ->
			order.getCreatedAt() != null &&
			order.getUpdatedAt() != null
		));
	}

	@Test
	void shouldThrowExceptionWhenProductsListIsNull() {
		checkoutRequest.setProdutos(null);

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
			orderService.createOrder(checkoutRequest)
		);

		assertEquals("Lista de produtos não pode estar vazia", exception.getMessage());
		verify(orderRepository, never()).save(any(Order.class));
	}

	@Test
	void shouldThrowExceptionWhenProductsListIsEmpty() {
		checkoutRequest.setProdutos(Collections.emptyList());

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
			orderService.createOrder(checkoutRequest)
		);

		assertEquals("Lista de produtos não pode estar vazia", exception.getMessage());
		verify(orderRepository, never()).save(any(Order.class));
	}

	@Test
	void shouldThrowExceptionWhenQuantityIsZero() {
		CheckoutRequest.ItemProduto item = new CheckoutRequest.ItemProduto();
		item.setIdProduto(1L);
		item.setQuantidade(0);
		item.setPrecoUnitario(BigDecimal.TEN);
		checkoutRequest.setProdutos(Collections.singletonList(item));

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
			orderService.createOrder(checkoutRequest)
		);

		assertEquals("Quantidade de produtos deve ser maior que zero", exception.getMessage());
		verify(orderRepository, never()).save(any(Order.class));
	}

	@Test
	void shouldThrowExceptionWhenQuantityIsNegative() {
		CheckoutRequest.ItemProduto item = new CheckoutRequest.ItemProduto();
		item.setIdProduto(1L);
		item.setQuantidade(-1);
		item.setPrecoUnitario(BigDecimal.TEN);
		checkoutRequest.setProdutos(Collections.singletonList(item));

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
			orderService.createOrder(checkoutRequest)
		);

		assertEquals("Quantidade de produtos deve ser maior que zero", exception.getMessage());
		verify(orderRepository, never()).save(any(Order.class));
	}

	@Test
	void shouldThrowExceptionWhenPaymentMethodIsNull() {
		checkoutRequest.setMetodoPagamento(null);

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
			orderService.createOrder(checkoutRequest)
		);

		assertEquals("Método de pagamento inválido", exception.getMessage());
		verify(orderRepository, never()).save(any(Order.class));
	}

	@Test
	void shouldThrowExceptionWhenPaymentMethodIsInvalid() {
		checkoutRequest.setMetodoPagamento("BITCOIN");

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
			orderService.createOrder(checkoutRequest)
		);

		assertEquals("Método de pagamento inválido", exception.getMessage());
		verify(orderRepository, never()).save(any(Order.class));
	}

	@Test
	void shouldAcceptValidPaymentMethods() {
		Order savedOrder = new Order();
		when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

		// Test PIX
		checkoutRequest.setMetodoPagamento("PIX");
		assertDoesNotThrow(() -> orderService.createOrder(checkoutRequest));

		// Test CARTAO
		checkoutRequest.setMetodoPagamento("CARTAO");
		assertDoesNotThrow(() -> orderService.createOrder(checkoutRequest));

		// Test DINHEIRO
		checkoutRequest.setMetodoPagamento("DINHEIRO");
		assertDoesNotThrow(() -> orderService.createOrder(checkoutRequest));

		// Test lowercase
		checkoutRequest.setMetodoPagamento("pix");
		assertDoesNotThrow(() -> orderService.createOrder(checkoutRequest));

		verify(orderRepository, times(4)).save(any(Order.class));
	}

	@Test
	void shouldCalculateSubtotalForEachItem() {
		Order savedOrder = new Order();
		when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

		CheckoutRequest.ItemProduto item = new CheckoutRequest.ItemProduto();
		item.setIdProduto(1L);
		item.setQuantidade(3);
		item.setPrecoUnitario(BigDecimal.valueOf(25.50));
		checkoutRequest.setProdutos(Collections.singletonList(item));

		orderService.createOrder(checkoutRequest);

		verify(orderRepository).save(argThat(order ->
			order.getItems().get(0).getSubtotal().equals(BigDecimal.valueOf(76.50))
		));
	}
}


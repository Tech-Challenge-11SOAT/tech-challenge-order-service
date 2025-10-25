package br.com.postech.techchallange_order.application.services;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.postech.techchallange_order.domain.enums.StatusPagamentoEnum;
import br.com.postech.techchallange_order.domain.enums.StatusPedidoEnum;
import br.com.postech.techchallange_order.domain.model.Order;
import br.com.postech.techchallange_order.domain.ports.in.OrderUseCase;
import br.com.postech.techchallange_order.domain.ports.out.OrderRepositoryPort;
import br.com.postech.techchallange_order.infrastructure.adapters.in.rest.dto.CheckoutRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService implements OrderUseCase {

	private final OrderRepositoryPort orderRepository;

	@Override
	public Order createOrder(CheckoutRequest request) {
		this.validateRequest(request);

		Order order = new Order();
		order.setCustomerId(request.getIdCliente());
		order.setOrderDate(Instant.now());
		order.setItems(this.mapItems(request));
		order.setPayment(this.mapPayment(request));
		order.setStatus(this.mapStatus());

		order.setCreatedAt(Instant.now());
		order.setUpdatedAt(Instant.now());

		return orderRepository.save(order);
	}

	private List<Order.Item> mapItems(CheckoutRequest request) {
		return request.getProdutos().stream()
				.map(itemReq -> {
					Order.Item item = new Order.Item();
					item.setProductId(itemReq.getIdProduto());
					item.setQuantity(itemReq.getQuantidade());
					item.setUnitPrice(itemReq.getPrecoUnitario());
					return item;
				})
				.toList();
	}

	private Order.Payment mapPayment(CheckoutRequest request) {
		Order.Payment payment = new Order.Payment();
		payment.setPaymentMethod(request.getMetodoPagamento());
		payment.setTotalAmount(this.calcularTotalPedido(request.getProdutos()));
		payment.setPaymentDate(null);

		Order.Payment.PaymentStatus status = new Order.Payment.PaymentStatus();
		status.setName(StatusPagamentoEnum.PENDENTE.getStatus());

		payment.setStatus(status);
		return payment;
	}

	private BigDecimal calcularTotalPedido(List<CheckoutRequest.ItemProduto> produtos) {
		return produtos.stream()
				.map(item -> item.getPrecoUnitario().multiply(new BigDecimal(item.getQuantidade())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private void validateRequest(CheckoutRequest request) {
		if (request.getProdutos() == null || request.getProdutos().isEmpty()) {
			throw new IllegalArgumentException("Lista de produtos não pode estar vazia");
		}

		for (CheckoutRequest.ItemProduto produto : request.getProdutos()) {
			if (produto.getQuantidade() <= 0) {
				throw new IllegalArgumentException("Quantidade de produtos deve ser maior que zero");
			}
		}

		if (request.getMetodoPagamento() == null
				|| !Arrays.asList("PIX", "CARTAO", "DINHEIRO").contains(request.getMetodoPagamento().toUpperCase())) {
			throw new IllegalArgumentException("Método de pagamento inválido");
		}
	}

	private Order.Status mapStatus() {
		Order.Status newStatus = new Order.Status();
		newStatus.setName(StatusPedidoEnum.RECEBIDO.getStatus());
		newStatus.setUpdatedAt(Instant.now());
		return newStatus;
	}

}

package br.com.postech.techchallange_order.domain.model;

import br.com.postech.techchallange_order.infrastructure.adapters.out.external.mercadopago.dto.OrderResponseDTO;

public class Checkout {
	private Long idPedido;
	private Long idPagamento;
	private String metodoPagamento;
	private String status;
	private Integer numeroPedido;
	private OrderResponseDTO orderResponse;

	public Checkout() {
	}

	public Checkout(Long idPedido, Long idPagamento, String metodoPagamento, String status, Integer numeroPedido,
			OrderResponseDTO orderResponse) {
		this.idPedido = idPedido;
		this.idPagamento = idPagamento;
		this.metodoPagamento = metodoPagamento;
		this.status = status;
		this.numeroPedido = numeroPedido;
		this.orderResponse = orderResponse;
	}

	public Long getIdPedido() {
		return this.idPedido;
	}

	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}

	public Long getIdPagamento() {
		return this.idPagamento;
	}

	public void setIdPagamento(Long idPagamento) {
		this.idPagamento = idPagamento;
	}

	public String getMetodoPagamento() {
		return this.metodoPagamento;
	}

	public void setMetodoPagamento(String metodoPagamento) {
		this.metodoPagamento = metodoPagamento;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getNumeroPedido() {
		return this.numeroPedido;
	}

	public void setNumeroPedido(Integer numeroPedido) {
		this.numeroPedido = numeroPedido;
	}

	public OrderResponseDTO getOrderResponse() {
		return this.orderResponse;
	}

	public void setOrderResponse(OrderResponseDTO orderResponse) {
		this.orderResponse = orderResponse;
	}

}

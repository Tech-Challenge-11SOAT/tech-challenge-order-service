package br.com.postech.techchallange_order.infrastructure.adapters.in.rest.dto.response;

import br.com.postech.techchallange_order.infrastructure.adapters.out.external.mercadopago.dto.OrderResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutResponse {
	private String idPedido;
	private Long idPagamento;
	private String metodoPagamento;
	private String status;
	private Integer numeroPedido;
	private OrderResponseDTO orderResponse;
	private String qrCode;
	private String qrCodeBase64;
}
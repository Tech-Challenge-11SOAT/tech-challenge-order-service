package br.com.postech.techchallange_order.domain.enums;

import java.util.Arrays;

public enum StatusPedidoEnum {
	RECEBIDO("RECEBIDO"),
	RECEBIDO_NAO_PAGO("RECEBIDO - Não pago"),
	EM_ANDAMENTO("EM_ANDAMENTO"),
	FINALIZADO("FINALIZADO"),
	CANCELADO("CANCELADO");

	private final String status;

	private StatusPedidoEnum(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public static StatusPedidoEnum of(String status) {
		if (status == null) {
			return null;
		}
		return Arrays.stream(StatusPedidoEnum.values())
				.filter(st -> st.getStatus().equals(status))
				.findFirst()
				.orElse(null);
	}
}
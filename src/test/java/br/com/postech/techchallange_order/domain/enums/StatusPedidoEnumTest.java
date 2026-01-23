package br.com.postech.techchallange_order.domain.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatusPedidoEnumTest {

	@Test
	void shouldHaveRecebidoStatus() {
		StatusPedidoEnum status = StatusPedidoEnum.RECEBIDO;

		assertNotNull(status);
		assertEquals("RECEBIDO", status.getStatus());
	}

	@Test
	void shouldHaveRecebidoNaoPagoStatus() {
		StatusPedidoEnum status = StatusPedidoEnum.RECEBIDO_NAO_PAGO;

		assertNotNull(status);
		assertEquals("RECEBIDO - Não pago", status.getStatus());
	}

	@Test
	void shouldHaveEmAndamentoStatus() {
		StatusPedidoEnum status = StatusPedidoEnum.EM_ANDAMENTO;

		assertNotNull(status);
		assertEquals("EM_ANDAMENTO", status.getStatus());
	}

	@Test
	void shouldHaveFinalizadoStatus() {
		StatusPedidoEnum status = StatusPedidoEnum.FINALIZADO;

		assertNotNull(status);
		assertEquals("FINALIZADO", status.getStatus());
	}

	@Test
	void shouldHaveCanceladoStatus() {
		StatusPedidoEnum status = StatusPedidoEnum.CANCELADO;

		assertNotNull(status);
		assertEquals("CANCELADO", status.getStatus());
	}

	@Test
	void shouldHaveFiveStatuses() {
		StatusPedidoEnum[] statuses = StatusPedidoEnum.values();

		assertEquals(5, statuses.length);
	}

	@Test
	void shouldGetStatusByName() {
		StatusPedidoEnum status = StatusPedidoEnum.valueOf("RECEBIDO");

		assertEquals(StatusPedidoEnum.RECEBIDO, status);
		assertEquals("RECEBIDO", status.getStatus());
	}

	@Test
	void shouldThrowExceptionForInvalidStatus() {
		assertThrows(IllegalArgumentException.class, () -> {
			StatusPedidoEnum.valueOf("INVALID_STATUS");
		});
	}

	@Test
	void shouldReturnStatusFromOf() {
		StatusPedidoEnum status = StatusPedidoEnum.of("RECEBIDO");

		assertEquals(StatusPedidoEnum.RECEBIDO, status);
	}

	@Test
	void shouldReturnStatusFromOfWithComplexString() {
		StatusPedidoEnum status = StatusPedidoEnum.of("RECEBIDO - Não pago");

		assertEquals(StatusPedidoEnum.RECEBIDO_NAO_PAGO, status);
	}

	@Test
	void shouldReturnNullFromOfWhenStatusIsNull() {
		StatusPedidoEnum status = StatusPedidoEnum.of(null);

		assertNull(status);
	}

	@Test
	void shouldReturnNullFromOfWhenStatusNotFound() {
		StatusPedidoEnum status = StatusPedidoEnum.of("NON_EXISTENT_STATUS");

		assertNull(status);
	}

	@Test
	void shouldCompareStatuses() {
		StatusPedidoEnum status1 = StatusPedidoEnum.RECEBIDO;
		StatusPedidoEnum status2 = StatusPedidoEnum.RECEBIDO;
		StatusPedidoEnum status3 = StatusPedidoEnum.FINALIZADO;

		assertEquals(status1, status2);
		assertNotEquals(status1, status3);
	}

	@Test
	void shouldGetOrdinalValues() {
		assertEquals(0, StatusPedidoEnum.RECEBIDO.ordinal());
		assertEquals(1, StatusPedidoEnum.RECEBIDO_NAO_PAGO.ordinal());
		assertEquals(2, StatusPedidoEnum.EM_ANDAMENTO.ordinal());
		assertEquals(3, StatusPedidoEnum.FINALIZADO.ordinal());
		assertEquals(4, StatusPedidoEnum.CANCELADO.ordinal());
	}

	@Test
	void shouldUseInSwitchStatement() {
		StatusPedidoEnum status = StatusPedidoEnum.EM_ANDAMENTO;
		String result;

		switch (status) {
			case RECEBIDO:
				result = "Order received";
				break;
			case RECEBIDO_NAO_PAGO:
				result = "Order received but not paid";
				break;
			case EM_ANDAMENTO:
				result = "Order in progress";
				break;
			case FINALIZADO:
				result = "Order finalized";
				break;
			case CANCELADO:
				result = "Order cancelled";
				break;
			default:
				result = "Unknown status";
		}

		assertEquals("Order in progress", result);
	}

	@Test
	void shouldIterateOverAllStatuses() {
		StatusPedidoEnum[] expectedStatuses = {
			StatusPedidoEnum.RECEBIDO,
			StatusPedidoEnum.RECEBIDO_NAO_PAGO,
			StatusPedidoEnum.EM_ANDAMENTO,
			StatusPedidoEnum.FINALIZADO,
			StatusPedidoEnum.CANCELADO
		};

		StatusPedidoEnum[] actualStatuses = StatusPedidoEnum.values();

		assertArrayEquals(expectedStatuses, actualStatuses);
	}

	@Test
	void shouldConvertToString() {
		String statusString = StatusPedidoEnum.RECEBIDO.toString();

		assertEquals("RECEBIDO", statusString);
	}
}


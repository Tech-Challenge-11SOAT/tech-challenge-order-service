package br.com.postech.techchallange_order.domain.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatusPagamentoEnumTest {

	@Test
	void shouldHavePendenteStatus() {
		StatusPagamentoEnum status = StatusPagamentoEnum.PENDENTE;

		assertNotNull(status);
		assertEquals("PENDENTE", status.getStatus());
	}

	@Test
	void shouldHaveErroStatus() {
		StatusPagamentoEnum status = StatusPagamentoEnum.ERRO;

		assertNotNull(status);
		assertEquals("ERRO", status.getStatus());
	}

	@Test
	void shouldHaveFinalizadoStatus() {
		StatusPagamentoEnum status = StatusPagamentoEnum.FINALIZADO;

		assertNotNull(status);
		assertEquals("FINALIZADO", status.getStatus());
	}

	@Test
	void shouldHaveThreeStatuses() {
		StatusPagamentoEnum[] statuses = StatusPagamentoEnum.values();

		assertEquals(3, statuses.length);
	}

	@Test
	void shouldGetStatusByName() {
		StatusPagamentoEnum status = StatusPagamentoEnum.valueOf("PENDENTE");

		assertEquals(StatusPagamentoEnum.PENDENTE, status);
		assertEquals("PENDENTE", status.getStatus());
	}

	@Test
	void shouldThrowExceptionForInvalidStatus() {
		assertThrows(IllegalArgumentException.class, () -> StatusPagamentoEnum.valueOf("INVALID_STATUS"));
	}

	@Test
	void shouldReturnStatusFromOf() {
		StatusPagamentoEnum status = StatusPagamentoEnum.of("PENDENTE");

		assertEquals(StatusPagamentoEnum.PENDENTE, status);
	}

	@Test
	void shouldReturnNullFromOfWhenStatusIsNull() {
		StatusPagamentoEnum status = StatusPagamentoEnum.of(null);

		assertNull(status);
	}

	@Test
	void shouldCompareStatuses() {
		StatusPagamentoEnum status1 = StatusPagamentoEnum.PENDENTE;
		StatusPagamentoEnum status2 = StatusPagamentoEnum.PENDENTE;
		StatusPagamentoEnum status3 = StatusPagamentoEnum.FINALIZADO;

		assertEquals(status1, status2);
		assertNotEquals(status1, status3);
	}

	@Test
	void shouldGetOrdinalValues() {
		assertEquals(0, StatusPagamentoEnum.PENDENTE.ordinal());
		assertEquals(1, StatusPagamentoEnum.ERRO.ordinal());
		assertEquals(2, StatusPagamentoEnum.FINALIZADO.ordinal());
	}
}


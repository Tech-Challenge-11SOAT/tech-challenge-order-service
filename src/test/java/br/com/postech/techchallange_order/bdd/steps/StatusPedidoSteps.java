package br.com.postech.techchallange_order.bdd.steps;

import br.com.postech.techchallange_order.bdd.context.TestContext;
import br.com.postech.techchallange_order.domain.enums.StatusPedidoEnum;
import io.cucumber.java.pt.*;
import lombok.RequiredArgsConstructor;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
public class StatusPedidoSteps {

	private final TestContext testContext;
	private StatusPedidoEnum statusEnum;

	@Dado("que existe um enum de status de pedido")
	public void queExisteUmEnumDeStatusDePedido() {
		assertNotNull(StatusPedidoEnum.values());
		assertTrue(StatusPedidoEnum.values().length > 0);
	}

	@Quando("consultar os status disponíveis")
	public void consultarOsStatusDisponiveis() {
		// Apenas valida que o enum está acessível
		statusEnum = StatusPedidoEnum.RECEBIDO;
	}

	@Então("deve existir o status {string}")
	public void deveExistirOStatus(String statusName) {
		StatusPedidoEnum found = StatusPedidoEnum.of(statusName);
		assertNotNull(found, "Status " + statusName + " deve existir");
		assertEquals(statusName, found.getStatus());
	}
}

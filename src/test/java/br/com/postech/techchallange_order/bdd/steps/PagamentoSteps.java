package br.com.postech.techchallange_order.bdd.steps;

import br.com.postech.techchallange_order.bdd.context.TestContext;
import br.com.postech.techchallange_order.domain.model.PaymentTransaction;
import io.cucumber.java.pt.*;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
public class PagamentoSteps {

	private final TestContext testContext;
	private PaymentTransaction transaction;

	@Dado("que o sistema de pagamento está disponível")
	public void queOSistemaDePagamentoEstaDisponivel() {
		testContext.reset();
	}

	@Quando("criar uma nova transação de pagamento")
	public void criarUmaNovaTransacaoDePagamento() {
		transaction = new PaymentTransaction();
		transaction.setOrderId("order-123");
		transaction.setAmount(new BigDecimal("100.00"));
		transaction.setPaymentMethod("PIX");
		transaction.setStatus("PENDENTE");
		transaction.setCreatedAt(Instant.now());
	}

	@Então("a transação deve ter todos os campos obrigatórios")
	public void aTransacaoDeveTerTodosOsCamposObrigatorios() {
		assertNotNull(transaction);
		assertNotNull(transaction.getOrderId());
		assertNotNull(transaction.getAmount());
		assertNotNull(transaction.getPaymentMethod());
		assertNotNull(transaction.getStatus());
		assertNotNull(transaction.getCreatedAt());
	}
}

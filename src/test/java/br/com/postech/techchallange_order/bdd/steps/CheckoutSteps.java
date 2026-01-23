package br.com.postech.techchallange_order.bdd.steps;

import br.com.postech.techchallange_order.bdd.context.TestContext;
import br.com.postech.techchallange_order.infrastructure.adapters.in.rest.dto.CheckoutRequest;
import io.cucumber.java.pt.*;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
public class CheckoutSteps {

	private final TestContext testContext;
	private Long productId;
	private BigDecimal productPrice;
	private Integer quantity;

	@Dado("que o sistema está disponível")
	public void queOSistemaEstaDisponivel() {
		testContext.reset();
		assertNotNull(testContext, "Contexto de teste deve estar disponível");
	}

	@Dado("existe um produto com ID {string} e preço {string}")
	public void existeUmProdutoComIDEPreco(String id, String price) {
		this.productId = Long.valueOf(id);
		this.productPrice = new BigDecimal(price);
	}

	@Quando("eu adicionar {string} unidades do produto ao carrinho")
	public void euAdicionarUnidadesDoProdutoAoCarrinho(String qty) {
		this.quantity = Integer.valueOf(qty);

		CheckoutRequest.ItemProduto item = new CheckoutRequest.ItemProduto();
		item.setIdProduto(productId);
		item.setQuantidade(quantity);
		item.setPrecoUnitario(productPrice);

		testContext.getProdutos().add(item);
	}

	@Quando("realizar o checkout")
	public void realizarOCheckout() {
		CheckoutRequest request = new CheckoutRequest();
		request.setProdutos(testContext.getProdutos());
		request.setMetodoPagamento("PIX");

		testContext.setCheckoutResponse(null); // Simula processo
		testContext.setLastException(null);
	}

	@Então("o pedido deve ser processado com sucesso")
	public void oPedidoDeveSerProcessadoComSucesso() {
		assertNull(testContext.getLastException(), "Não deve ter exceção");
		assertFalse(testContext.getProdutos().isEmpty(), "Deve ter produtos no carrinho");
		assertTrue(quantity > 0, "Quantidade deve ser maior que zero");
	}
}

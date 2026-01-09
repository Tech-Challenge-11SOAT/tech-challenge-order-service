package br.com.postech.techchallange_order.bdd.context;

import br.com.postech.techchallange_order.domain.model.Order;
import br.com.postech.techchallange_order.infrastructure.adapters.in.rest.dto.CheckoutRequest;
import br.com.postech.techchallange_order.infrastructure.adapters.in.rest.dto.response.CheckoutResponse;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class TestContext {
	private String customerId;
	private List<CheckoutRequest.ItemProduto> produtos = new ArrayList<>();
	private String paymentMethod;
	private CheckoutResponse checkoutResponse;
	private Order createdOrder;
	private Exception lastException;
	private String orderId;
	private String paymentStatus;
	private String orderStatus;
	private List<Order.Status> statusHistory = new ArrayList<>();

	public void reset() {
		customerId = null;
		produtos = new ArrayList<>();
		paymentMethod = null;
		checkoutResponse = null;
		createdOrder = null;
		lastException = null;
		orderId = null;
		paymentStatus = null;
		orderStatus = null;
		statusHistory = new ArrayList<>();
	}
}

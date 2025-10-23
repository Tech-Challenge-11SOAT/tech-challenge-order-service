package br.com.postech.techchallange_order.domain.ports.in;

import java.util.List;
import java.util.Optional;

import br.com.postech.techchallange_order.domain.model.Order;

public interface ManageOrderUseCase {
	Order createOrder(Order order);

	Optional<Order> getOrderById(String orderId);

	List<Order> getAllOrders();

	void deleteOrder(String orderId);
}
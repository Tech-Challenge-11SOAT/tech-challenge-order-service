package br.com.postech.techchallange_order.domain.ports.out;

import br.com.postech.techchallange_order.domain.model.Order;
import java.util.List;
import java.util.Optional;

public interface OrderRepository {
	Order save(Order order);

	Optional<Order> findById(String orderId);

	List<Order> findAll();

	void deleteById(String orderId);
}
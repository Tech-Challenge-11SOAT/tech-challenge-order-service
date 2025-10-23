package br.com.postech.techchallange_order.application.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.postech.techchallange_order.domain.model.Order;
import br.com.postech.techchallange_order.domain.ports.in.ManageOrderUseCase;
import br.com.postech.techchallange_order.domain.ports.out.OrderRepository;

@Service
public class OrderService implements ManageOrderUseCase {

	private final OrderRepository orderRepository;

	public OrderService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@Override
	public Order createOrder(Order order) {
		return orderRepository.save(order);
	}

	@Override
	public Optional<Order> getOrderById(String orderId) {
		return orderRepository.findById(orderId);
	}

	@Override
	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}

	@Override
	public void deleteOrder(String orderId) {
		orderRepository.deleteById(orderId);
	}
}
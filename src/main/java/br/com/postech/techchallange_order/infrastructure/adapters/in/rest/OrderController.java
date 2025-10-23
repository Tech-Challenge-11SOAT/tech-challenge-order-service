package br.com.postech.techchallange_order.infrastructure.adapters.in.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.postech.techchallange_order.domain.model.Order;
import br.com.postech.techchallange_order.domain.ports.in.ManageOrderUseCase;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	private final ManageOrderUseCase manageOrderUseCase;

	public OrderController(ManageOrderUseCase manageOrderUseCase) {
		this.manageOrderUseCase = manageOrderUseCase;
	}

	@PostMapping
	public ResponseEntity<Order> createOrder(@RequestBody Order order) {
		return ResponseEntity.ok(manageOrderUseCase.createOrder(order));
	}

	@GetMapping("/{orderId}")
	public ResponseEntity<Order> getOrder(@PathVariable String orderId) {
		return manageOrderUseCase.getOrderById(orderId)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping
	public ResponseEntity<List<Order>> getAllOrders() {
		return ResponseEntity.ok(manageOrderUseCase.getAllOrders());
	}

	@DeleteMapping("/{orderId}")
	public ResponseEntity<Void> deleteOrder(@PathVariable String orderId) {
		manageOrderUseCase.deleteOrder(orderId);
		return ResponseEntity.noContent().build();
	}
}
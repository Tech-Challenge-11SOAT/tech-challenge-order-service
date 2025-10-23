package br.com.postech.techchallange_order.infrastructure.adapters.in.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.postech.techchallange_order.domain.model.Checkout;
import br.com.postech.techchallange_order.domain.ports.in.CheckouUseCase;
import br.com.postech.techchallange_order.infrastructure.adapters.in.rest.dto.CheckoutRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/checkout")
@RequiredArgsConstructor
public class CheckoutController {

	private final CheckouUseCase checkoutUseCase;

	@PostMapping
	public ResponseEntity<Checkout> processCheckout(@Valid @RequestBody CheckoutRequest request) {
		Checkout checkout = this.checkoutUseCase.processarCheckout(request);
		return ResponseEntity.ok(checkout);
	}
}
package br.com.postech.techchallange_order.infrastructure.adapters.in.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.postech.techchallange_order.domain.ports.in.CheckouUseCase;
import br.com.postech.techchallange_order.infrastructure.adapters.in.rest.dto.CheckoutRequest;
import br.com.postech.techchallange_order.infrastructure.adapters.in.rest.dto.response.CheckoutResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/checkout")
@RequiredArgsConstructor
public class CheckoutController {

	private final CheckouUseCase checkoutUseCase;

	@PostMapping
	public ResponseEntity<CheckoutResponse> processCheckout(@Valid @RequestBody CheckoutRequest request) {
		CheckoutResponse checkout = this.checkoutUseCase.processarCheckout(request);
		return ResponseEntity.ok(checkout);
	}
}
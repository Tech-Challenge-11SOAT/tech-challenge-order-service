package br.com.postech.techchallange_order.infrastructure.adapters.in.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.postech.techchallange_order.domain.model.PaymentTransaction;
import br.com.postech.techchallange_order.domain.ports.in.PaymentUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentsController {

	private final PaymentUseCase paymentUseCase;

	@PostMapping("/notify")
	public ResponseEntity<Void> notifyPayment(@Valid @RequestBody PaymentTransaction transaction) {
		paymentUseCase.processPaymentTransaction(transaction);
		return ResponseEntity.ok().build();
	}
}

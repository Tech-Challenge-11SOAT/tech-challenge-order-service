package br.com.postech.techchallange_order.infrastructure.adapters.in.rest;

import br.com.postech.techchallange_order.domain.model.PaymentTransaction;
import br.com.postech.techchallange_order.domain.ports.in.PaymentUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.Instant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentsController.class)
class PaymentsControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockitoBean
	private PaymentUseCase paymentUseCase;

	private PaymentTransaction paymentTransaction;

	@BeforeEach
	void setUp() {
		paymentTransaction = new PaymentTransaction();
		paymentTransaction.setOrderId("order-123");
		paymentTransaction.setTransactionId("trans-456");
		paymentTransaction.setAmount(BigDecimal.valueOf(100.50));
		paymentTransaction.setPaymentMethod("PIX");
		paymentTransaction.setStatus("FINALIZADO");
		paymentTransaction.setCreatedAt(Instant.now());
	}

	@Test
	void shouldNotifyPaymentSuccessfully() throws Exception {
		doNothing().when(paymentUseCase).processPaymentTransaction(any(PaymentTransaction.class));

		mockMvc.perform(post("/api/payments/notify")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(paymentTransaction)))
			.andExpect(status().isOk());

		verify(paymentUseCase, times(1)).processPaymentTransaction(any(PaymentTransaction.class));
	}

	@Test
	void shouldNotifyPaymentWithDifferentStatuses() throws Exception {
		doNothing().when(paymentUseCase).processPaymentTransaction(any(PaymentTransaction.class));

		paymentTransaction.setStatus("PENDENTE");
		mockMvc.perform(post("/api/payments/notify")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(paymentTransaction)))
			.andExpect(status().isOk());

		paymentTransaction.setStatus("ERRO");
		mockMvc.perform(post("/api/payments/notify")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(paymentTransaction)))
			.andExpect(status().isOk());

		paymentTransaction.setStatus("FINALIZADO");
		mockMvc.perform(post("/api/payments/notify")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(paymentTransaction)))
			.andExpect(status().isOk());

		verify(paymentUseCase, times(3)).processPaymentTransaction(any(PaymentTransaction.class));
	}

	@Test
	void shouldNotifyPaymentWithDifferentPaymentMethods() throws Exception {
		doNothing().when(paymentUseCase).processPaymentTransaction(any(PaymentTransaction.class));

		paymentTransaction.setPaymentMethod("PIX");
		mockMvc.perform(post("/api/payments/notify")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(paymentTransaction)))
			.andExpect(status().isOk());

		paymentTransaction.setPaymentMethod("CARTAO");
		mockMvc.perform(post("/api/payments/notify")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(paymentTransaction)))
			.andExpect(status().isOk());

		verify(paymentUseCase, times(2)).processPaymentTransaction(any(PaymentTransaction.class));
	}

	@Test
	void shouldNotifyPaymentWithMinimalData() throws Exception {
		PaymentTransaction minimalTransaction = new PaymentTransaction();
		minimalTransaction.setOrderId("order-789");
		minimalTransaction.setAmount(BigDecimal.TEN);

		doNothing().when(paymentUseCase).processPaymentTransaction(any(PaymentTransaction.class));

		mockMvc.perform(post("/api/payments/notify")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(minimalTransaction)))
			.andExpect(status().isOk());

		verify(paymentUseCase, times(1)).processPaymentTransaction(any(PaymentTransaction.class));
	}
}


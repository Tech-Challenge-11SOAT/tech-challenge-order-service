package br.com.postech.techchallange_order.application.services;

import br.com.postech.techchallange_order.domain.model.PaymentTransaction;
import br.com.postech.techchallange_order.infrastructure.adapters.out.mongodb.mapper.PaymentTransactionMapper;
import br.com.postech.techchallange_order.infrastructure.adapters.out.mongodb.model.PaymentTransactionDocument;
import br.com.postech.techchallange_order.infrastructure.adapters.out.persistence.PaymentTransactionMongoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentTransactionServiceTest {

	@Mock
	private PaymentTransactionMapper paymentTransactionMapper;

	@Mock
	private PaymentTransactionMongoRepository paymentTransactionRepository;

	@InjectMocks
	private PaymentTransactionService paymentTransactionService;

	private PaymentTransaction transaction;
	private PaymentTransactionDocument document;

	@BeforeEach
	void setUp() {
		transaction = new PaymentTransaction();
		transaction.setOrderId("order-123");
		transaction.setTransactionId("trans-456");
		transaction.setAmount(BigDecimal.valueOf(150.50));
		transaction.setPaymentMethod("PIX");
		transaction.setStatus("PENDENTE");
		transaction.setCreatedAt(Instant.now());

		document = new PaymentTransactionDocument();
	}

	@Test
	void shouldCreatePaymentTransactionSuccessfully() {
		when(paymentTransactionMapper.toDocument(transaction)).thenReturn(document);
		when(paymentTransactionRepository.save(document)).thenReturn(document);

		paymentTransactionService.createPaymentTransaction(transaction);

		verify(paymentTransactionMapper, times(1)).toDocument(transaction);
		verify(paymentTransactionRepository, times(1)).save(document);
	}

	@Test
	void shouldMapTransactionToDocument() {
		when(paymentTransactionMapper.toDocument(any(PaymentTransaction.class))).thenReturn(document);
		when(paymentTransactionRepository.save(any(PaymentTransactionDocument.class))).thenReturn(document);

		paymentTransactionService.createPaymentTransaction(transaction);

		verify(paymentTransactionMapper, times(1)).toDocument(transaction);
	}

	@Test
	void shouldSaveDocument() {
		when(paymentTransactionMapper.toDocument(transaction)).thenReturn(document);
		when(paymentTransactionRepository.save(document)).thenReturn(document);

		paymentTransactionService.createPaymentTransaction(transaction);

		verify(paymentTransactionRepository, times(1)).save(document);
	}

	@Test
	void shouldHandleTransactionWithNullFields() {
		PaymentTransaction emptyTransaction = new PaymentTransaction();
		when(paymentTransactionMapper.toDocument(emptyTransaction)).thenReturn(document);
		when(paymentTransactionRepository.save(document)).thenReturn(document);

		paymentTransactionService.createPaymentTransaction(emptyTransaction);

		verify(paymentTransactionMapper, times(1)).toDocument(emptyTransaction);
		verify(paymentTransactionRepository, times(1)).save(document);
	}
}


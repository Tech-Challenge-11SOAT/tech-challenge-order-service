package br.com.postech.techchallange_order.application.services;

import br.com.postech.techchallange_order.domain.enums.StatusPagamentoEnum;
import br.com.postech.techchallange_order.domain.model.OrderQueueItem;
import br.com.postech.techchallange_order.domain.model.PaymentTransaction;
import br.com.postech.techchallange_order.domain.ports.in.PaymentTransactionUseCase;
import br.com.postech.techchallange_order.domain.ports.out.QueueRepositoryPort;
import br.com.postech.techchallange_order.infrastructure.adapters.out.mongodb.model.OrderDocument;
import br.com.postech.techchallange_order.infrastructure.adapters.out.persistence.OrderMongoRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

	@Mock
	private PaymentTransactionUseCase paymentTransactionUseCase;

	@Mock
	private OrderMongoRepository orderMongoRepository;

	@Mock
	private QueueRepositoryPort queueRepository;

	@InjectMocks
	private PaymentService paymentService;

	private PaymentTransaction transaction;

	@BeforeEach
	void setUp() {
		transaction = new PaymentTransaction();
		transaction.setOrderId(new ObjectId().toString());
		transaction.setAmount(BigDecimal.valueOf(100));
		transaction.setPaymentMethod("PIX");
		transaction.setStatus(StatusPagamentoEnum.PENDENTE.getStatus());
		transaction.setCreatedAt(Instant.now());
	}

	@Test
	void shouldProcessPaymentTransactionWithPendenteStatus() {
		paymentService.processPaymentTransaction(transaction);

		verify(paymentTransactionUseCase, times(1)).createPaymentTransaction(transaction);
		verify(queueRepository, never()).save(any(OrderQueueItem.class));
	}

	@Test
	void shouldProcessPaymentTransactionWithFinalizadoStatusAndCreateQueue() {
		transaction.setStatus(StatusPagamentoEnum.FINALIZADO.getStatus());

		OrderDocument orderDoc = new OrderDocument();
		orderDoc.setCustomerId("customer-123");

		when(orderMongoRepository.findById(any(ObjectId.class))).thenReturn(Optional.of(orderDoc));
		when(queueRepository.save(any(OrderQueueItem.class))).thenReturn(new OrderQueueItem());

		paymentService.processPaymentTransaction(transaction);

		verify(paymentTransactionUseCase, times(1)).createPaymentTransaction(transaction);
		verify(orderMongoRepository, times(1)).findById(any(ObjectId.class));
		verify(queueRepository, times(1)).save(argThat(item ->
			item.getOrderId().equals(transaction.getOrderId()) &&
			item.getCustomerId().equals("customer-123") &&
			item.getStatus().equals("QUEUED") &&
			item.getCreatedAt() != null
		));
	}

	@Test
	void shouldProcessPaymentTransactionWithFinalizadoStatusButOrderNotFound() {
		transaction.setStatus(StatusPagamentoEnum.FINALIZADO.getStatus());

		when(orderMongoRepository.findById(any(ObjectId.class))).thenReturn(Optional.empty());
		when(queueRepository.save(any(OrderQueueItem.class))).thenReturn(new OrderQueueItem());

		paymentService.processPaymentTransaction(transaction);

		verify(paymentTransactionUseCase, times(1)).createPaymentTransaction(transaction);
		verify(orderMongoRepository, times(1)).findById(any(ObjectId.class));
		verify(queueRepository, times(1)).save(argThat(item ->
			item.getOrderId().equals(transaction.getOrderId()) &&
			item.getCustomerId() == null &&
			item.getStatus().equals("QUEUED")
		));
	}

	@Test
	void shouldProcessPaymentTransactionWithFinalizadoStatusButInvalidObjectId() {
		transaction.setStatus(StatusPagamentoEnum.FINALIZADO.getStatus());
		transaction.setOrderId("invalid-object-id");

		when(queueRepository.save(any(OrderQueueItem.class))).thenReturn(new OrderQueueItem());

		paymentService.processPaymentTransaction(transaction);

		verify(paymentTransactionUseCase, times(1)).createPaymentTransaction(transaction);
		verify(orderMongoRepository, never()).findById(any(ObjectId.class));
		verify(queueRepository, times(1)).save(argThat(item ->
			item.getOrderId().equals("invalid-object-id") &&
			item.getCustomerId() == null &&
			item.getStatus().equals("QUEUED")
		));
	}

	@Test
	void shouldProcessPaymentTransactionWithFinalizadoStatusButNullOrderId() {
		transaction.setStatus(StatusPagamentoEnum.FINALIZADO.getStatus());
		transaction.setOrderId(null);

		when(queueRepository.save(any(OrderQueueItem.class))).thenReturn(new OrderQueueItem());

		paymentService.processPaymentTransaction(transaction);

		verify(paymentTransactionUseCase, times(1)).createPaymentTransaction(transaction);
		verify(orderMongoRepository, never()).findById(any(ObjectId.class));
		verify(queueRepository, times(1)).save(argThat(item ->
			item.getOrderId() == null &&
			item.getCustomerId() == null &&
			item.getStatus().equals("QUEUED")
		));
	}

	@Test
	void shouldProcessPaymentTransactionWithErroStatus() {
		transaction.setStatus(StatusPagamentoEnum.ERRO.getStatus());

		paymentService.processPaymentTransaction(transaction);

		verify(paymentTransactionUseCase, times(1)).createPaymentTransaction(transaction);
		verify(queueRepository, never()).save(any(OrderQueueItem.class));
	}

	@Test
	void shouldProcessPaymentTransactionWithNullStatus() {
		transaction.setStatus(null);

		paymentService.processPaymentTransaction(transaction);

		verify(paymentTransactionUseCase, times(1)).createPaymentTransaction(transaction);
		verify(queueRepository, never()).save(any(OrderQueueItem.class));
	}

	@Test
	void shouldHandleRepositoryExceptionGracefully() {
		transaction.setStatus(StatusPagamentoEnum.FINALIZADO.getStatus());

		when(orderMongoRepository.findById(any(ObjectId.class))).thenThrow(new RuntimeException("Database error"));
		when(queueRepository.save(any(OrderQueueItem.class))).thenReturn(new OrderQueueItem());

		paymentService.processPaymentTransaction(transaction);

		verify(paymentTransactionUseCase, times(1)).createPaymentTransaction(transaction);
		verify(queueRepository, times(1)).save(argThat(item ->
			item.getCustomerId() == null &&
			item.getStatus().equals("QUEUED")
		));
	}

	@Test
	void shouldProcessPaymentWithFinalizadoInDifferentCase() {
		transaction.setStatus("FINALIZADO");

		OrderDocument orderDoc = new OrderDocument();
		orderDoc.setCustomerId("customer-789");

		when(orderMongoRepository.findById(any(ObjectId.class))).thenReturn(Optional.of(orderDoc));
		when(queueRepository.save(any(OrderQueueItem.class))).thenReturn(new OrderQueueItem());

		paymentService.processPaymentTransaction(transaction);

		verify(queueRepository, times(1)).save(any(OrderQueueItem.class));
	}
}


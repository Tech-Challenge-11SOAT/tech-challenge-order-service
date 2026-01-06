package br.com.postech.techchallange_order.application.services;

import br.com.postech.techchallange_order.domain.model.Order;
import br.com.postech.techchallange_order.infrastructure.adapters.out.mongodb.mapper.OrderHistoryMapper;
import br.com.postech.techchallange_order.infrastructure.adapters.out.mongodb.model.OrderHistoryDocument;
import br.com.postech.techchallange_order.infrastructure.adapters.out.persistence.OrderHistoryMongoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderStatusHistoryServiceTest {

	@Mock
	private OrderHistoryMongoRepository orderHistoryRepository;

	@InjectMocks
	private OrderStatusHistoryService orderStatusHistoryService;

	private Order order;
	private Order.Status previousStatus;
	private Order.Status currentStatus;

	@BeforeEach
	void setUp() {
		order = new Order();
		order.setId("order-123");
		order.setCustomerId("customer-456");

		currentStatus = new Order.Status();
		currentStatus.setId(2L);
		currentStatus.setName("EM_ANDAMENTO");
		currentStatus.setUpdatedAt(Instant.now());
		order.setStatus(currentStatus);

		previousStatus = new Order.Status();
		previousStatus.setId(1L);
		previousStatus.setName("RECEBIDO");
		previousStatus.setUpdatedAt(Instant.now());
	}

	@Test
	void shouldRecordStatusChangeWithPreviousStatus() {
		OrderHistoryDocument historyDocument = new OrderHistoryDocument();

		try (MockedStatic<OrderHistoryMapper> mockedMapper = mockStatic(OrderHistoryMapper.class)) {
			mockedMapper.when(() -> OrderHistoryMapper.toDocument(order, previousStatus))
				.thenReturn(historyDocument);

			when(orderHistoryRepository.save(any(OrderHistoryDocument.class))).thenReturn(historyDocument);

			orderStatusHistoryService.recordStatusChange(order, previousStatus);

			mockedMapper.verify(() -> OrderHistoryMapper.toDocument(order, previousStatus), times(1));
			verify(orderHistoryRepository, times(1)).save(historyDocument);
		}
	}

	@Test
	void shouldRecordStatusChangeWithoutPreviousStatus() {
		OrderHistoryDocument historyDocument = new OrderHistoryDocument();

		try (MockedStatic<OrderHistoryMapper> mockedMapper = mockStatic(OrderHistoryMapper.class)) {
			mockedMapper.when(() -> OrderHistoryMapper.toDocument(eq(order), isNull()))
				.thenReturn(historyDocument);

			when(orderHistoryRepository.save(any(OrderHistoryDocument.class))).thenReturn(historyDocument);

			orderStatusHistoryService.recordStatusChange(order, null);

			mockedMapper.verify(() -> OrderHistoryMapper.toDocument(eq(order), isNull()), times(1));
			verify(orderHistoryRepository, times(1)).save(historyDocument);
		}
	}

	@Test
	void shouldSaveHistoryDocument() {
		OrderHistoryDocument historyDocument = new OrderHistoryDocument();

		try (MockedStatic<OrderHistoryMapper> mockedMapper = mockStatic(OrderHistoryMapper.class)) {
			mockedMapper.when(() -> OrderHistoryMapper.toDocument(any(Order.class), any()))
				.thenReturn(historyDocument);

			when(orderHistoryRepository.save(historyDocument)).thenReturn(historyDocument);

			orderStatusHistoryService.recordStatusChange(order, previousStatus);

			verify(orderHistoryRepository, times(1)).save(historyDocument);
		}
	}
}


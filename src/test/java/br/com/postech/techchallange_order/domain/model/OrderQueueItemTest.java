package br.com.postech.techchallange_order.domain.model;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class OrderQueueItemTest {

	@Test
	void shouldCreateOrderQueueItemWithNoArgsConstructor() {
		OrderQueueItem item = new OrderQueueItem();

		assertNotNull(item);
		assertNull(item.getId());
		assertNull(item.getOrderId());
		assertNull(item.getCustomerId());
		assertNull(item.getCreatedAt());
		assertNull(item.getStatus());
	}

	@Test
	void shouldCreateOrderQueueItemWithAllArgsConstructor() {
		Instant now = Instant.now();
		OrderQueueItem item = new OrderQueueItem("id-123", "order-456", "customer-789", now, "QUEUED");

		assertEquals("id-123", item.getId());
		assertEquals("order-456", item.getOrderId());
		assertEquals("customer-789", item.getCustomerId());
		assertEquals(now, item.getCreatedAt());
		assertEquals("QUEUED", item.getStatus());
	}

	@Test
	void shouldSetAndGetId() {
		OrderQueueItem item = new OrderQueueItem();
		item.setId("queue-id-001");

		assertEquals("queue-id-001", item.getId());
	}

	@Test
	void shouldSetAndGetOrderId() {
		OrderQueueItem item = new OrderQueueItem();
		item.setOrderId("order-002");

		assertEquals("order-002", item.getOrderId());
	}

	@Test
	void shouldSetAndGetCustomerId() {
		OrderQueueItem item = new OrderQueueItem();
		item.setCustomerId("customer-003");

		assertEquals("customer-003", item.getCustomerId());
	}

	@Test
	void shouldAllowNullCustomerId() {
		OrderQueueItem item = new OrderQueueItem();
		item.setCustomerId(null);

		assertNull(item.getCustomerId());
	}

	@Test
	void shouldSetAndGetCreatedAt() {
		OrderQueueItem item = new OrderQueueItem();
		Instant now = Instant.now();
		item.setCreatedAt(now);

		assertEquals(now, item.getCreatedAt());
	}

	@Test
	void shouldSetAndGetStatus() {
		OrderQueueItem item = new OrderQueueItem();
		item.setStatus("PROCESSING");

		assertEquals("PROCESSING", item.getStatus());
	}

	@Test
	void shouldHandleMultipleStatusValues() {
		OrderQueueItem item = new OrderQueueItem();

		item.setStatus("QUEUED");
		assertEquals("QUEUED", item.getStatus());

		item.setStatus("PROCESSING");
		assertEquals("PROCESSING", item.getStatus());

		item.setStatus("COMPLETED");
		assertEquals("COMPLETED", item.getStatus());
	}
}


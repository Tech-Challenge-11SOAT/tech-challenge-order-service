package br.com.postech.techchallange_order.infrastructure.adapters.out.mongodb.mapper;

import br.com.postech.techchallange_order.domain.model.Order;
import br.com.postech.techchallange_order.infrastructure.adapters.out.mongodb.model.OrderHistoryDocument;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class OrderHistoryMapperTest {

    @Test
    void shouldReturnNullWhenDomainIsNull() {
        assertNull(OrderHistoryMapper.toDocument(null, null));
    }

    @Test
    void shouldMapOrderToDocumentWithStatusAndPreviousStatus() {
        ObjectId objectId = new ObjectId();
        Instant now = Instant.now();

        Order.Status currentStatus = new Order.Status(2L, "EM_ANDAMENTO", now);
        Order.Status previousStatus = new Order.Status(1L, "RECEBIDO", now);

        Order order = new Order();
        order.setId(objectId.toHexString());
        order.setStatus(currentStatus);

        OrderHistoryDocument doc = OrderHistoryMapper.toDocument(order, previousStatus);

        assertNotNull(doc);
        assertEquals(objectId, doc.getOrderId());

        assertNotNull(doc.getStatus());
        assertEquals(2L, doc.getStatus().getId());
        assertEquals("EM_ANDAMENTO", doc.getStatus().getName());

        assertNotNull(doc.getPreviousStatus());
        assertEquals(1L, doc.getPreviousStatus().getId());
        assertEquals("RECEBIDO", doc.getPreviousStatus().getName());

        assertNotNull(doc.getCreatedAt());
    }

    @Test
    void shouldMapOrderToDocumentWithoutPreviousStatus() {
        ObjectId objectId = new ObjectId();
        Instant now = Instant.now();

        Order.Status currentStatus = new Order.Status(1L, "RECEBIDO", now);

        Order order = new Order();
        order.setId(objectId.toHexString());
        order.setStatus(currentStatus);

        OrderHistoryDocument doc = OrderHistoryMapper.toDocument(order, null);

        assertNotNull(doc);
        assertEquals(objectId, doc.getOrderId());

        assertNotNull(doc.getStatus());
        assertEquals(1L, doc.getStatus().getId());
        assertEquals("RECEBIDO", doc.getStatus().getName());

        assertNull(doc.getPreviousStatus());
        assertNotNull(doc.getCreatedAt());
    }

    @Test
    void shouldMapOrderWithNullIdAndStatus() {
        Order order = new Order();
        order.setOrderId(123L);

        OrderHistoryDocument doc = OrderHistoryMapper.toDocument(order, null);

        assertNotNull(doc);
        assertNull(doc.getOrderId());
        assertNull(doc.getStatus());
        assertNull(doc.getPreviousStatus());
        assertNotNull(doc.getCreatedAt());
    }

    @Test
    void shouldMapOrderWithNullStatus() {
        ObjectId objectId = new ObjectId();
        Order order = new Order();
        order.setId(objectId.toHexString());
        order.setStatus(null);

        Order.Status previousStatus = new Order.Status(1L, "RECEBIDO", Instant.now());

        OrderHistoryDocument doc = OrderHistoryMapper.toDocument(order, previousStatus);

        assertNotNull(doc);
        assertEquals(objectId, doc.getOrderId());
        assertNull(doc.getStatus());
        assertNotNull(doc.getPreviousStatus());
        assertEquals(1L, doc.getPreviousStatus().getId());
    }
}


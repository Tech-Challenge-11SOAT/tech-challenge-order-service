package br.com.postech.techchallange_order.infrastructure.adapters.out.mongodb.mapper;

import br.com.postech.techchallange_order.domain.model.Order;
import br.com.postech.techchallange_order.helpers.OrderMother;
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
        Order order = OrderMother.createCompleteOrder();
        Order.Status previousStatus = OrderMother.createRecebidoStatus();
        order.setStatus(OrderMother.createEmAndamentoStatus());

        OrderHistoryDocument doc = OrderHistoryMapper.toDocument(order, previousStatus);

        assertNotNull(doc);
        assertNotNull(doc.getOrderId());

        assertNotNull(doc.getStatus());
        assertEquals(order.getStatus().getId(), doc.getStatus().getId());
        assertEquals(order.getStatus().getName(), doc.getStatus().getName());

        assertNotNull(doc.getPreviousStatus());
        assertEquals(previousStatus.getId(), doc.getPreviousStatus().getId());
        assertEquals(previousStatus.getName(), doc.getPreviousStatus().getName());

        assertNotNull(doc.getCreatedAt());
    }

    @Test
    void shouldMapOrderToDocumentWithoutPreviousStatus() {
        Order order = OrderMother.createCompleteOrder();

        OrderHistoryDocument doc = OrderHistoryMapper.toDocument(order, null);

        assertNotNull(doc);
        assertNotNull(doc.getOrderId());

        assertNotNull(doc.getStatus());
        assertEquals(order.getStatus().getId(), doc.getStatus().getId());
        assertEquals(order.getStatus().getName(), doc.getStatus().getName());

        assertNull(doc.getPreviousStatus());
        assertNotNull(doc.getCreatedAt());
    }

    @Test
    void shouldMapOrderWithNullIdAndStatus() {
        Order order = OrderMother.createMinimalOrder();
        order.setId(null);
        order.setStatus(null);

        OrderHistoryDocument doc = OrderHistoryMapper.toDocument(order, null);

        assertNotNull(doc);
        assertNull(doc.getOrderId());
        assertNull(doc.getStatus());
        assertNull(doc.getPreviousStatus());
        assertNotNull(doc.getCreatedAt());
    }

    @Test
    void shouldMapOrderWithNullStatus() {
        Order order = OrderMother.createCompleteOrder();
        order.setStatus(null);

        Order.Status previousStatus = OrderMother.createRecebidoStatus();

        OrderHistoryDocument doc = OrderHistoryMapper.toDocument(order, previousStatus);

        assertNotNull(doc);
        assertNotNull(doc.getOrderId());
        assertNull(doc.getStatus());
        assertNotNull(doc.getPreviousStatus());
        assertEquals(previousStatus.getId(), doc.getPreviousStatus().getId());
    }
}


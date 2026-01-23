package br.com.postech.techchallange_order.infrastructure.adapters.out.mongodb.mapper;

import br.com.postech.techchallange_order.domain.model.OrderQueueItem;
import br.com.postech.techchallange_order.infrastructure.adapters.out.mongodb.model.OrderQueueDocument;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class OrderQueueMapperTest {

    private OrderQueueMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new OrderQueueMapper();
    }

    @Test
    void shouldReturnNullWhenItemIsNull() {
        assertNull(mapper.toDocument(null));
    }

    @Test
    void shouldReturnNullWhenDocumentIsNull() {
        assertNull(mapper.toDomain(null));
    }

    @Test
    void shouldMapItemToDocument() {
        Instant now = Instant.now();
        ObjectId id = new ObjectId();
        ObjectId orderId = new ObjectId();

        OrderQueueItem item = new OrderQueueItem();
        item.setId(id.toHexString());
        item.setOrderId(orderId.toHexString());
        item.setCustomerId("customer-123");
        item.setCreatedAt(now);
        item.setStatus("QUEUED");

        OrderQueueDocument doc = mapper.toDocument(item);

        assertNotNull(doc);
        assertEquals(id, doc.getId());
        assertEquals(orderId, doc.getOrderId());
        assertEquals("customer-123", doc.getCustomerId());
        assertEquals(now, doc.getCreatedAt());
        assertEquals("QUEUED", doc.getStatus());
    }

    @Test
    void shouldMapDocumentToItem() {
        Instant now = Instant.now();
        ObjectId id = new ObjectId();
        ObjectId orderId = new ObjectId();

        OrderQueueDocument doc = new OrderQueueDocument();
        doc.setId(id);
        doc.setOrderId(orderId);
        doc.setCustomerId("customer-456");
        doc.setCreatedAt(now);
        doc.setStatus("PROCESSING");

        OrderQueueItem item = mapper.toDomain(doc);

        assertNotNull(item);
        assertEquals(id.toHexString(), item.getId());
        assertEquals(orderId.toHexString(), item.getOrderId());
        assertEquals("customer-456", item.getCustomerId());
        assertEquals(now, item.getCreatedAt());
        assertEquals("PROCESSING", item.getStatus());
    }

    @Test
    void shouldMapItemWithNullFieldsToDocument() {
        OrderQueueItem item = new OrderQueueItem();
        item.setStatus("QUEUED");

        OrderQueueDocument doc = mapper.toDocument(item);

        assertNotNull(doc);
        assertNull(doc.getId());
        assertNull(doc.getOrderId());
        assertNull(doc.getCustomerId());
        assertEquals("QUEUED", doc.getStatus());
    }

    @Test
    void shouldMapDocumentWithNullFieldsToItem() {
        OrderQueueDocument doc = new OrderQueueDocument();
        doc.setStatus("COMPLETED");

        OrderQueueItem item = mapper.toDomain(doc);

        assertNotNull(item);
        assertNull(item.getId());
        assertNull(item.getOrderId());
        assertNull(item.getCustomerId());
        assertEquals("COMPLETED", item.getStatus());
    }
}


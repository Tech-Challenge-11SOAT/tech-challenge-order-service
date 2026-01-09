package br.com.postech.techchallange_order.infrastructure.adapters.out.persistence;

import br.com.postech.techchallange_order.domain.model.OrderQueueItem;
import br.com.postech.techchallange_order.infrastructure.adapters.out.mongodb.mapper.OrderQueueMapper;
import br.com.postech.techchallange_order.infrastructure.adapters.out.mongodb.model.OrderQueueDocument;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MongoQueueAdapterTest {

    @Mock
    private OrderQueueMongoRepository repository;

    @Mock
    private OrderQueueMapper mapper;

    @InjectMocks
    private MongoQueueAdapter mongoQueueAdapter;

    private OrderQueueItem queueItem;
    private OrderQueueDocument queueDocument;

    @BeforeEach
    void setUp() {
        queueItem = new OrderQueueItem();
        queueItem.setId(new ObjectId().toHexString());
        queueItem.setOrderId(new ObjectId().toHexString());
        queueItem.setCustomerId("customer-123");
        queueItem.setCreatedAt(Instant.now());
        queueItem.setStatus("QUEUED");

        queueDocument = new OrderQueueDocument();
        queueDocument.setId(new ObjectId());
        queueDocument.setOrderId(new ObjectId());
        queueDocument.setCustomerId("customer-123");
        queueDocument.setCreatedAt(Instant.now());
        queueDocument.setStatus("QUEUED");
    }

    @Test
    void shouldSaveQueueItemSuccessfully() {
        when(mapper.toDocument(queueItem)).thenReturn(queueDocument);
        when(repository.save(queueDocument)).thenReturn(queueDocument);
        when(mapper.toDomain(queueDocument)).thenReturn(queueItem);

        OrderQueueItem result = mongoQueueAdapter.save(queueItem);

        assertNotNull(result);
        assertEquals(queueItem.getOrderId(), result.getOrderId());
        assertEquals(queueItem.getCustomerId(), result.getCustomerId());

        verify(mapper, times(1)).toDocument(queueItem);
        verify(repository, times(1)).save(queueDocument);
        verify(mapper, times(1)).toDomain(queueDocument);
    }

    @Test
    void shouldHandleQueueItemWithoutCustomerId() {
        OrderQueueItem itemWithoutCustomer = new OrderQueueItem();
        itemWithoutCustomer.setOrderId(new ObjectId().toHexString());
        itemWithoutCustomer.setStatus("QUEUED");

        OrderQueueDocument docWithoutCustomer = new OrderQueueDocument();
        docWithoutCustomer.setOrderId(new ObjectId());
        docWithoutCustomer.setStatus("QUEUED");

        when(mapper.toDocument(itemWithoutCustomer)).thenReturn(docWithoutCustomer);
        when(repository.save(docWithoutCustomer)).thenReturn(docWithoutCustomer);
        when(mapper.toDomain(docWithoutCustomer)).thenReturn(itemWithoutCustomer);

        OrderQueueItem result = mongoQueueAdapter.save(itemWithoutCustomer);

        assertNotNull(result);
        verify(mapper, times(1)).toDocument(itemWithoutCustomer);
        verify(repository, times(1)).save(docWithoutCustomer);
        verify(mapper, times(1)).toDomain(docWithoutCustomer);
    }
}

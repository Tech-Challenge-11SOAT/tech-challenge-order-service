package br.com.postech.techchallange_order.infrastructure.adapters.out.persistence;

import org.bson.types.ObjectId;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.postech.techchallange_order.domain.model.Order;
import br.com.postech.techchallange_order.helpers.OrderMother;
import br.com.postech.techchallange_order.infrastructure.adapters.out.mongodb.model.OrderDocument;

@ExtendWith(MockitoExtension.class)
class MongoOrderAdapterTest {

    @Mock
    private MongoOrderRepository repository;

    @InjectMocks
    private MongoOrderAdapter mongoOrderAdapter;

    private Order order;
    private OrderDocument orderDocument;

    @BeforeEach
    void setUp() {
        order = OrderMother.createCompleteOrder();

        orderDocument = new OrderDocument();
        orderDocument.setId(new ObjectId());
        orderDocument.setOrderId(order.getOrderId());
        orderDocument.setCustomerId(order.getCustomerId());
    }

    @Test
    void shouldSaveOrderSuccessfully() {
        when(repository.save(any(OrderDocument.class))).thenReturn(orderDocument);

        Order result = mongoOrderAdapter.save(order);

        assertNotNull(result);
        verify(repository, times(1)).save(any(OrderDocument.class));
    }

    @Test
    void shouldConvertDomainToDocumentAndBack() {
        when(repository.save(any(OrderDocument.class))).thenReturn(orderDocument);

        Order result = mongoOrderAdapter.save(order);

        assertNotNull(result);
        assertEquals(order.getOrderId(), result.getOrderId());
        assertEquals(order.getCustomerId(), result.getCustomerId());
        verify(repository, times(1)).save(any(OrderDocument.class));
    }

    @Test
    void shouldHandleMinimalOrder() {
        Order minimalOrder = OrderMother.createMinimalOrder();
        OrderDocument minimalDoc = new OrderDocument();
        minimalDoc.setId(new ObjectId());

        when(repository.save(any(OrderDocument.class))).thenReturn(minimalDoc);

        Order result = mongoOrderAdapter.save(minimalOrder);

        assertNotNull(result);
        verify(repository, times(1)).save(any(OrderDocument.class));
    }
}

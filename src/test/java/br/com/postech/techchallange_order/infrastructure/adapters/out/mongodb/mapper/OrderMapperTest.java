package br.com.postech.techchallange_order.infrastructure.adapters.out.mongodb.mapper;

import br.com.postech.techchallange_order.domain.model.Order;
import br.com.postech.techchallange_order.helpers.OrderMother;
import br.com.postech.techchallange_order.infrastructure.adapters.out.mongodb.model.OrderDocument;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class OrderMapperTest {

    @Test
    void shouldReturnNullWhenDomainIsNull() {
        assertNull(OrderMapper.toDocument(null));
    }

    @Test
    void shouldReturnNullWhenDocumentIsNull() {
        assertNull(OrderMapper.toDomain(null));
    }

    @Test
    void shouldMapDomainToDocument() {
        Order order = OrderMother.createOrderWithMercadoPago();

        OrderDocument doc = OrderMapper.toDocument(order);

        assertNotNull(doc);
        assertNotNull(doc.getId());
        assertNotNull(doc.getOrderId());
        assertEquals(order.getCustomerId(), doc.getCustomerId());
        assertEquals(order.getOrderDate(), doc.getOrderDate());

        assertNotNull(doc.getStatus());
        assertEquals(order.getStatus().getId(), doc.getStatus().getId());
        assertEquals(order.getStatus().getName(), doc.getStatus().getName());

        assertEquals(order.getQueuePosition(), doc.getQueuePosition());
        assertEquals(order.getItems().size(), doc.getItems().size());
        assertEquals(order.getItems().get(0).getProductId(), doc.getItems().get(0).getProductId());
        assertEquals(order.getItems().get(0).getQuantity(), doc.getItems().get(0).getQuantity());

        assertNotNull(doc.getPayment());
        assertEquals(order.getPayment().getPaymentId(), doc.getPayment().getPaymentId());
        assertEquals(order.getPayment().getPaymentMethod(), doc.getPayment().getPaymentMethod());

        assertNotNull(doc.getPayment().getMercadoPagoInfo());
        assertEquals(order.getPayment().getMercadoPagoInfo().getOrderId(), doc.getPayment().getMercadoPagoInfo().getOrderId());
        assertEquals(order.getPayment().getMercadoPagoInfo().getQrCode(), doc.getPayment().getMercadoPagoInfo().getQrCode());
    }

    @Test
    void shouldMapDocumentToDomain() {
        Instant now = Instant.now();
        ObjectId objectId = new ObjectId();

        OrderDocument.Status status = new OrderDocument.Status();
        status.setId(2L);
        status.setName("EM_ANDAMENTO");
        status.setUpdatedAt(now);

        OrderDocument.Item item = new OrderDocument.Item();
        item.setProductId(20L);
        item.setQuantity(3);
        item.setUnitPrice(new Decimal128(BigDecimal.valueOf(15.00)));
        item.setSubtotal(new Decimal128(BigDecimal.valueOf(45.00)));

        OrderDocument.PaymentStatus paymentStatus = new OrderDocument.PaymentStatus();
        paymentStatus.setId(2L);
        paymentStatus.setName("FINALIZADO");

        OrderDocument.MercadoPagoInfo mpInfo = new OrderDocument.MercadoPagoInfo();
        mpInfo.setOrderId("mp-doc-456");
        mpInfo.setStatus("approved");
        mpInfo.setStatusDetail("accredited");
        mpInfo.setExternalReference("ext-ref-doc");
        mpInfo.setQrCode("qr-code-doc");
        mpInfo.setQrCodeBase64("qr-base64-doc");
        mpInfo.setTicketUrl("http://ticket-doc.url");

        OrderDocument.Payment payment = new OrderDocument.Payment();
        payment.setPaymentId(200L);
        payment.setTotalAmount(new Decimal128(BigDecimal.valueOf(45.00)));
        payment.setPaymentMethod("CARTAO");
        payment.setStatus(paymentStatus);
        payment.setPaymentDate(now);
        payment.setMercadoPagoInfo(mpInfo);

        OrderDocument doc = new OrderDocument();
        doc.setId(objectId);
        doc.setOrderId(456L);
        doc.setCustomerId("customer-789");
        doc.setOrderDate(now);
        doc.setStatus(status);
        doc.setQueuePosition(2);
        doc.setItems(Collections.singletonList(item));
        doc.setPayment(payment);
        doc.setCreatedAt(now);
        doc.setUpdatedAt(now);

        Order order = OrderMapper.toDomain(doc);

        assertNotNull(order);
        assertEquals(objectId.toHexString(), order.getId());
        assertEquals(456L, order.getOrderId());
        assertEquals("customer-789", order.getCustomerId());
        assertEquals(now, order.getOrderDate());

        assertNotNull(order.getStatus());
        assertEquals(2L, order.getStatus().getId());
        assertEquals("EM_ANDAMENTO", order.getStatus().getName());

        assertEquals(2, order.getQueuePosition());
        assertEquals(1, order.getItems().size());
        assertEquals(20L, order.getItems().get(0).getProductId());
        assertEquals(3, order.getItems().get(0).getQuantity());

        assertNotNull(order.getPayment());
        assertEquals(200L, order.getPayment().getPaymentId());
        assertEquals("CARTAO", order.getPayment().getPaymentMethod());

        assertNotNull(order.getPayment().getMercadoPagoInfo());
        assertEquals("mp-doc-456", order.getPayment().getMercadoPagoInfo().getOrderId());
        assertEquals("qr-code-doc", order.getPayment().getMercadoPagoInfo().getQrCode());
    }

    @Test
    void shouldMapDomainWithNullFieldsToDocument() {
        Order order = OrderMother.createMinimalOrder();

        OrderDocument doc = OrderMapper.toDocument(order);

        assertNotNull(doc);
        assertNotNull(doc.getOrderId());
        assertNull(doc.getId());
        assertNull(doc.getStatus());
        assertNull(doc.getPayment());
    }

    @Test
    void shouldMapDocumentWithNullFieldsToDomain() {
        OrderDocument doc = new OrderDocument();
        doc.setOrderId(888L);

        Order order = OrderMapper.toDomain(doc);

        assertNotNull(order);
        assertEquals(888L, order.getOrderId());
        assertNull(order.getId());
        assertNull(order.getStatus());
        assertNull(order.getPayment());
    }

    @Test
    void shouldMapItemsWithNullPrices() {
        Order order = OrderMother.createMinimalOrder();
        Order.Item item = new Order.Item();
        item.setProductId(1L);
        item.setQuantity(1);
        item.setUnitPrice(null);
        item.setSubtotal(null);
        order.setItems(Collections.singletonList(item));

        OrderDocument doc = OrderMapper.toDocument(order);

        assertNotNull(doc.getItems());
        assertEquals(1, doc.getItems().size());
        assertNull(doc.getItems().get(0).getUnitPrice());
        assertNull(doc.getItems().get(0).getSubtotal());
    }

    @Test
    void shouldMapPaymentWithNullStatusAndMercadoPagoInfo() {
        Order order = OrderMother.createMinimalOrder();
        Order.Payment payment = new Order.Payment();
        payment.setPaymentId(100L);
        payment.setPaymentMethod("PIX");
        payment.setStatus(null);
        payment.setMercadoPagoInfo(null);
        order.setPayment(payment);

        OrderDocument doc = OrderMapper.toDocument(order);

        assertNotNull(doc.getPayment());
        assertEquals(100L, doc.getPayment().getPaymentId());
        assertNull(doc.getPayment().getStatus());
        assertNull(doc.getPayment().getMercadoPagoInfo());
    }

    @Test
    void shouldMapDocumentPaymentWithNullStatusAndMercadoPagoInfo() {
        OrderDocument doc = new OrderDocument();
        OrderDocument.Payment payment = new OrderDocument.Payment();
        payment.setPaymentId(200L);
        payment.setPaymentMethod("CARTAO");
        payment.setStatus(null);
        payment.setMercadoPagoInfo(null);
        doc.setPayment(payment);

        Order order = OrderMapper.toDomain(doc);

        assertNotNull(order.getPayment());
        assertEquals(200L, order.getPayment().getPaymentId());
        assertNull(order.getPayment().getStatus());
        assertNull(order.getPayment().getMercadoPagoInfo());
    }

    @Test
    void shouldMapDocumentItemsWithNullPrices() {
        OrderDocument doc = new OrderDocument();
        OrderDocument.Item item = new OrderDocument.Item();
        item.setProductId(1L);
        item.setQuantity(1);
        item.setUnitPrice(null);
        item.setSubtotal(null);
        doc.setItems(Collections.singletonList(item));

        Order order = OrderMapper.toDomain(doc);

        assertNotNull(order.getItems());
        assertEquals(1, order.getItems().size());
        assertNull(order.getItems().get(0).getUnitPrice());
        assertNull(order.getItems().get(0).getSubtotal());
    }
}


package br.com.postech.techchallange_order.infrastructure.adapters.out.mongodb.mapper;

import br.com.postech.techchallange_order.domain.model.Order;
import br.com.postech.techchallange_order.infrastructure.adapters.out.mongodb.model.OrderDocument;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
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
        Instant now = Instant.now();

        Order.Status status = new Order.Status(1L, "RECEBIDO", now);
        Order.Item item = new Order.Item(10L, 2, BigDecimal.valueOf(25.50), BigDecimal.valueOf(51.00));
        Order.Payment.PaymentStatus paymentStatus = new Order.Payment.PaymentStatus(1L, "PENDENTE");
        Order.MercadoPagoInfo mpInfo = new Order.MercadoPagoInfo(
            "mp-order-123", "approved", "accredited", "ext-ref",
            "qr-code", "qr-base64", "http://ticket.url"
        );
        Order.Payment payment = new Order.Payment(100L, BigDecimal.valueOf(51.00), "PIX", paymentStatus, now, mpInfo);

        Order order = new Order();
        order.setId(new ObjectId().toHexString());
        order.setOrderId(123L);
        order.setCustomerId("customer-456");
        order.setOrderDate(now);
        order.setStatus(status);
        order.setQueuePosition(1);
        order.setItems(Arrays.asList(item));
        order.setPayment(payment);
        order.setCreatedAt(now);
        order.setUpdatedAt(now);

        OrderDocument doc = OrderMapper.toDocument(order);

        assertNotNull(doc);
        assertEquals(order.getId(), doc.getId().toHexString());
        assertEquals(123L, doc.getOrderId());
        assertEquals("customer-456", doc.getCustomerId());
        assertEquals(now, doc.getOrderDate());

        assertNotNull(doc.getStatus());
        assertEquals(1L, doc.getStatus().getId());
        assertEquals("RECEBIDO", doc.getStatus().getName());

        assertEquals(1, doc.getQueuePosition());
        assertEquals(1, doc.getItems().size());
        assertEquals(10L, doc.getItems().get(0).getProductId());
        assertEquals(2, doc.getItems().get(0).getQuantity());

        assertNotNull(doc.getPayment());
        assertEquals(100L, doc.getPayment().getPaymentId());
        assertEquals("PIX", doc.getPayment().getPaymentMethod());

        assertNotNull(doc.getPayment().getMercadoPagoInfo());
        assertEquals("mp-order-123", doc.getPayment().getMercadoPagoInfo().getOrderId());
        assertEquals("qr-code", doc.getPayment().getMercadoPagoInfo().getQrCode());
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
        doc.setItems(Arrays.asList(item));
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
        Order order = new Order();
        order.setOrderId(999L);

        OrderDocument doc = OrderMapper.toDocument(order);

        assertNotNull(doc);
        assertEquals(999L, doc.getOrderId());
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
        Order order = new Order();
        Order.Item item = new Order.Item();
        item.setProductId(1L);
        item.setQuantity(1);
        item.setUnitPrice(null);
        item.setSubtotal(null);
        order.setItems(Arrays.asList(item));

        OrderDocument doc = OrderMapper.toDocument(order);

        assertNotNull(doc.getItems());
        assertEquals(1, doc.getItems().size());
        assertNull(doc.getItems().get(0).getUnitPrice());
        assertNull(doc.getItems().get(0).getSubtotal());
    }

    @Test
    void shouldMapPaymentWithNullStatusAndMercadoPagoInfo() {
        Order order = new Order();
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
        doc.setItems(Arrays.asList(item));

        Order order = OrderMapper.toDomain(doc);

        assertNotNull(order.getItems());
        assertEquals(1, order.getItems().size());
        assertNull(order.getItems().get(0).getUnitPrice());
        assertNull(order.getItems().get(0).getSubtotal());
    }
}


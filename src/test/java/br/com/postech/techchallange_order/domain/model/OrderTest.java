package br.com.postech.techchallange_order.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void shouldCreateOrderWithNoArgsConstructor() {
        Order order = new Order();

        assertNotNull(order);
        assertNull(order.getId());
        assertNull(order.getOrderId());
        assertNull(order.getCustomerId());
        assertNotNull(order.getItems());
        assertTrue(order.getItems().isEmpty());
    }

    @Test
    void shouldCreateOrderWithAllArgsConstructor() {
        Instant now = Instant.now();
        Order.Status status = new Order.Status(1L, "RECEBIDO", now);
        Order.Item item = new Order.Item(1L, 2, BigDecimal.TEN, BigDecimal.valueOf(20));
        List<Order.Item> items = Collections.singletonList(item);
        Order.Payment payment = new Order.Payment();

        Order order = new Order("123", 456L, "customer1", now, status, 1, items, payment, now, now);

        assertEquals("123", order.getId());
        assertEquals(456L, order.getOrderId());
        assertEquals("customer1", order.getCustomerId());
        assertEquals(now, order.getOrderDate());
        assertEquals(status, order.getStatus());
        assertEquals(1, order.getQueuePosition());
        assertEquals(1, order.getItems().size());
        assertEquals(payment, order.getPayment());
    }

    @Test
    void shouldSetAndGetId() {
        Order order = new Order();
        order.setId("order-123");

        assertEquals("order-123", order.getId());
    }

    @Test
    void shouldSetAndGetOrderId() {
        Order order = new Order();
        order.setOrderId(999L);

        assertEquals(999L, order.getOrderId());
    }

    @Test
    void shouldSetAndGetCustomerId() {
        Order order = new Order();
        order.setCustomerId("cust-456");

        assertEquals("cust-456", order.getCustomerId());
    }

    @Test
    void shouldSetAndGetOrderDate() {
        Order order = new Order();
        Instant now = Instant.now();
        order.setOrderDate(now);

        assertEquals(now, order.getOrderDate());
    }

    @Test
    void shouldSetAndGetStatus() {
        Order order = new Order();
        Order.Status status = new Order.Status(1L, "RECEBIDO", Instant.now());
        order.setStatus(status);

        assertEquals(status, order.getStatus());
    }

    @Test
    void shouldSetAndGetQueuePosition() {
        Order order = new Order();
        order.setQueuePosition(5);

        assertEquals(5, order.getQueuePosition());
    }

    @Test
    void shouldSetAndGetItems() {
        Order order = new Order();
        Order.Item item = new Order.Item(1L, 3, BigDecimal.valueOf(15), BigDecimal.valueOf(45));
        List<Order.Item> items = Collections.singletonList(item);
        order.setItems(items);

        assertEquals(1, order.getItems().size());
        assertEquals(item, order.getItems().get(0));
    }

    @Test
    void shouldInitializeItemsAsEmptyListWhenNull() {
        Order order = new Order();
        order.setItems(null);

        assertNotNull(order.getItems());
        assertTrue(order.getItems().isEmpty());
    }

    @Test
    void shouldSetAndGetPayment() {
        Order order = new Order();
        Order.Payment payment = new Order.Payment();
        payment.setPaymentId(100L);
        order.setPayment(payment);

        assertEquals(payment, order.getPayment());
    }

    @Test
    void shouldSetAndGetCreatedAt() {
        Order order = new Order();
        Instant now = Instant.now();
        order.setCreatedAt(now);

        assertEquals(now, order.getCreatedAt());
    }

    @Test
    void shouldSetAndGetUpdatedAt() {
        Order order = new Order();
        Instant now = Instant.now();
        order.setUpdatedAt(now);

        assertEquals(now, order.getUpdatedAt());
    }

    @Test
    void shouldCreateStatusWithNoArgsConstructor() {
        Order.Status status = new Order.Status();

        assertNotNull(status);
        assertNull(status.getId());
        assertNull(status.getName());
        assertNull(status.getUpdatedAt());
    }

    @Test
    void shouldCreateStatusWithAllArgsConstructor() {
        Instant now = Instant.now();
        Order.Status status = new Order.Status(1L, "RECEBIDO", now);

        assertEquals(1L, status.getId());
        assertEquals("RECEBIDO", status.getName());
        assertEquals(now, status.getUpdatedAt());
    }

    @Test
    void shouldSetAndGetStatusFields() {
        Order.Status status = new Order.Status();
        Instant now = Instant.now();
        status.setId(2L);
        status.setName("EM_ANDAMENTO");
        status.setUpdatedAt(now);

        assertEquals(2L, status.getId());
        assertEquals("EM_ANDAMENTO", status.getName());
        assertEquals(now, status.getUpdatedAt());
    }

    @Test
    void shouldCreateItemWithNoArgsConstructor() {
        Order.Item item = new Order.Item();

        assertNotNull(item);
        assertNull(item.getProductId());
        assertNull(item.getQuantity());
        assertNull(item.getUnitPrice());
        assertNull(item.getSubtotal());
    }

    @Test
    void shouldCreateItemWithAllArgsConstructor() {
        Order.Item item = new Order.Item(10L, 5, BigDecimal.valueOf(20), BigDecimal.valueOf(100));

        assertEquals(10L, item.getProductId());
        assertEquals(5, item.getQuantity());
        assertEquals(BigDecimal.valueOf(20), item.getUnitPrice());
        assertEquals(BigDecimal.valueOf(100), item.getSubtotal());
    }

    @Test
    void shouldSetAndGetItemFields() {
        Order.Item item = new Order.Item();
        item.setProductId(15L);
        item.setQuantity(3);
        item.setUnitPrice(BigDecimal.valueOf(25.50));
        item.setSubtotal(BigDecimal.valueOf(76.50));

        assertEquals(15L, item.getProductId());
        assertEquals(3, item.getQuantity());
        assertEquals(BigDecimal.valueOf(25.50), item.getUnitPrice());
        assertEquals(BigDecimal.valueOf(76.50), item.getSubtotal());
    }

    @Test
    void shouldCreatePaymentWithNoArgsConstructor() {
        Order.Payment payment = new Order.Payment();

        assertNotNull(payment);
        assertNull(payment.getPaymentId());
        assertNull(payment.getTotalAmount());
        assertNull(payment.getPaymentMethod());
        assertNull(payment.getStatus());
        assertNull(payment.getPaymentDate());
        assertNull(payment.getMercadoPagoInfo());
    }

    @Test
    void shouldCreatePaymentWithAllArgsConstructor() {
        Instant now = Instant.now();
        Order.Payment.PaymentStatus status = new Order.Payment.PaymentStatus(1L, "PENDENTE");
        Order.MercadoPagoInfo mpInfo = new Order.MercadoPagoInfo();

        Order.Payment payment = new Order.Payment(100L, BigDecimal.valueOf(150), "PIX", status, now, mpInfo);

        assertEquals(100L, payment.getPaymentId());
        assertEquals(BigDecimal.valueOf(150), payment.getTotalAmount());
        assertEquals("PIX", payment.getPaymentMethod());
        assertEquals(status, payment.getStatus());
        assertEquals(now, payment.getPaymentDate());
        assertEquals(mpInfo, payment.getMercadoPagoInfo());
    }

    @Test
    void shouldSetAndGetPaymentFields() {
        Order.Payment payment = new Order.Payment();
        Instant now = Instant.now();
        Order.Payment.PaymentStatus status = new Order.Payment.PaymentStatus();
        Order.MercadoPagoInfo mpInfo = new Order.MercadoPagoInfo();

        payment.setPaymentId(200L);
        payment.setTotalAmount(BigDecimal.valueOf(250.75));
        payment.setPaymentMethod("CARTAO");
        payment.setStatus(status);
        payment.setPaymentDate(now);
        payment.setMercadoPagoInfo(mpInfo);

        assertEquals(200L, payment.getPaymentId());
        assertEquals(BigDecimal.valueOf(250.75), payment.getTotalAmount());
        assertEquals("CARTAO", payment.getPaymentMethod());
        assertEquals(status, payment.getStatus());
        assertEquals(now, payment.getPaymentDate());
        assertEquals(mpInfo, payment.getMercadoPagoInfo());
    }

    @Test
    void shouldCreatePaymentStatusWithNoArgsConstructor() {
        Order.Payment.PaymentStatus status = new Order.Payment.PaymentStatus();

        assertNotNull(status);
        assertNull(status.getId());
        assertNull(status.getName());
    }

    @Test
    void shouldCreatePaymentStatusWithAllArgsConstructor() {
        Order.Payment.PaymentStatus status = new Order.Payment.PaymentStatus(1L, "PENDENTE");

        assertEquals(1L, status.getId());
        assertEquals("PENDENTE", status.getName());
    }

    @Test
    void shouldSetAndGetPaymentStatusFields() {
        Order.Payment.PaymentStatus status = new Order.Payment.PaymentStatus();
        status.setId(2L);
        status.setName("FINALIZADO");

        assertEquals(2L, status.getId());
        assertEquals("FINALIZADO", status.getName());
    }

    @Test
    void shouldCreateMercadoPagoInfoWithNoArgsConstructor() {
        Order.MercadoPagoInfo info = new Order.MercadoPagoInfo();

        assertNotNull(info);
        assertNull(info.getOrderId());
        assertNull(info.getStatus());
        assertNull(info.getStatusDetail());
        assertNull(info.getExternalReference());
        assertNull(info.getQrCode());
        assertNull(info.getQrCodeBase64());
        assertNull(info.getTicketUrl());
    }

    @Test
    void shouldCreateMercadoPagoInfoWithAllArgsConstructor() {
        Order.MercadoPagoInfo info = new Order.MercadoPagoInfo(
            "mp-order-123", "approved", "accredited", "ref-456",
            "qr-code-data", "qr-base64", "http://ticket.url"
        );

        assertEquals("mp-order-123", info.getOrderId());
        assertEquals("approved", info.getStatus());
        assertEquals("accredited", info.getStatusDetail());
        assertEquals("ref-456", info.getExternalReference());
        assertEquals("qr-code-data", info.getQrCode());
        assertEquals("qr-base64", info.getQrCodeBase64());
        assertEquals("http://ticket.url", info.getTicketUrl());
    }

    @Test
    void shouldSetAndGetMercadoPagoInfoFields() {
        Order.MercadoPagoInfo info = new Order.MercadoPagoInfo();

        info.setOrderId("order-789");
        info.setStatus("pending");
        info.setStatusDetail("pending_payment");
        info.setExternalReference("ext-ref-123");
        info.setQrCode("qr-data");
        info.setQrCodeBase64("base64-data");
        info.setTicketUrl("http://example.com/ticket");

        assertEquals("order-789", info.getOrderId());
        assertEquals("pending", info.getStatus());
        assertEquals("pending_payment", info.getStatusDetail());
        assertEquals("ext-ref-123", info.getExternalReference());
        assertEquals("qr-data", info.getQrCode());
        assertEquals("base64-data", info.getQrCodeBase64());
        assertEquals("http://example.com/ticket", info.getTicketUrl());
    }
}


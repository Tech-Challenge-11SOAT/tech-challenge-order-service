package br.com.postech.techchallange_order.infrastructure.adapters.out.mongodb.mapper;

import br.com.postech.techchallange_order.domain.model.PaymentTransaction;
import br.com.postech.techchallange_order.infrastructure.adapters.out.mongodb.model.PaymentTransactionDocument;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTransactionMapperTest {

    private PaymentTransactionMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new PaymentTransactionMapper();
    }

    @Test
    void shouldReturnNullWhenDomainIsNull() {
        assertNull(mapper.toDocument(null));
    }

    @Test
    void shouldReturnNullWhenDocumentIsNull() {
        assertNull(mapper.toDomain(null));
    }

    @Test
    void shouldMapDomainToDocument() {
        Instant now = Instant.now();
        Map<String, Object> gatewayResponse = new HashMap<>();
        gatewayResponse.put("code", "200");
        gatewayResponse.put("message", "Success");

        PaymentTransaction transaction = new PaymentTransaction();
        transaction.setId(new ObjectId().toHexString());
        transaction.setOrderId(new ObjectId().toHexString());
        transaction.setTransactionId("trans-123");
        transaction.setAmount(BigDecimal.valueOf(150.50));
        transaction.setPaymentMethod("PIX");
        transaction.setStatus("FINALIZADO");
        transaction.setGatewayResponse(gatewayResponse);
        transaction.setCreatedAt(now);
        transaction.setUpdatedAt(now);

        PaymentTransactionDocument doc = mapper.toDocument(transaction);

        assertNotNull(doc);
        assertEquals(transaction.getId(), doc.getId().toHexString());
        assertEquals(transaction.getOrderId(), doc.getOrderId().toHexString());
        assertEquals("trans-123", doc.getTransactionId());
        assertEquals(new Decimal128(BigDecimal.valueOf(150.50)), doc.getAmount());
        assertEquals("PIX", doc.getPaymentMethod());
        assertEquals("FINALIZADO", doc.getStatus());
        assertEquals(gatewayResponse, doc.getGatewayResponse());
        assertEquals(now, doc.getCreatedAt());
        assertEquals(now, doc.getUpdatedAt());
    }

    @Test
    void shouldMapDocumentToDomain() {
        Instant now = Instant.now();
        ObjectId id = new ObjectId();
        ObjectId orderId = new ObjectId();
        Map<String, Object> gatewayResponse = new HashMap<>();
        gatewayResponse.put("status", "approved");

        PaymentTransactionDocument doc = PaymentTransactionDocument.builder()
                .id(id)
                .orderId(orderId)
                .transactionId("trans-456")
                .amount(new Decimal128(BigDecimal.valueOf(200.75)))
                .paymentMethod("CARTAO")
                .status("PENDENTE")
                .gatewayResponse(gatewayResponse)
                .createdAt(now)
                .updatedAt(now)
                .build();

        PaymentTransaction transaction = mapper.toDomain(doc);

        assertNotNull(transaction);
        assertEquals(id.toHexString(), transaction.getId());
        assertEquals(orderId.toHexString(), transaction.getOrderId());
        assertEquals("trans-456", transaction.getTransactionId());
        assertEquals(BigDecimal.valueOf(200.75), transaction.getAmount());
        assertEquals("CARTAO", transaction.getPaymentMethod());
        assertEquals("PENDENTE", transaction.getStatus());
        assertEquals(gatewayResponse, transaction.getGatewayResponse());
        assertEquals(now, transaction.getCreatedAt());
        assertEquals(now, transaction.getUpdatedAt());
    }

    @Test
    void shouldMapDomainWithNullFieldsToDocument() {
        PaymentTransaction transaction = new PaymentTransaction();
        transaction.setTransactionId("trans-null");

        PaymentTransactionDocument doc = mapper.toDocument(transaction);

        assertNotNull(doc);
        assertEquals("trans-null", doc.getTransactionId());
        assertNull(doc.getId());
        assertNull(doc.getOrderId());
        assertNull(doc.getAmount());
    }

    @Test
    void shouldMapDocumentWithNullFieldsToDomain() {
        PaymentTransactionDocument doc = PaymentTransactionDocument.builder()
                .transactionId("trans-null-doc")
                .build();

        PaymentTransaction transaction = mapper.toDomain(doc);

        assertNotNull(transaction);
        assertEquals("trans-null-doc", transaction.getTransactionId());
        assertNull(transaction.getId());
        assertNull(transaction.getOrderId());
        assertNull(transaction.getAmount());
    }
}


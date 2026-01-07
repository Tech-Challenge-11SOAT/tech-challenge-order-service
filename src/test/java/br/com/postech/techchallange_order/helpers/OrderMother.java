package br.com.postech.techchallange_order.helpers;

import br.com.postech.techchallange_order.domain.enums.StatusPagamentoEnum;
import br.com.postech.techchallange_order.domain.enums.StatusPedidoEnum;
import br.com.postech.techchallange_order.domain.model.Order;
import br.com.postech.techchallange_order.infrastructure.adapters.in.rest.dto.CheckoutRequest;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;

/**
 * Object Mother pattern para criação de objetos Order para testes
 * Centraliza a criação de objetos complexos, evitando duplicação
 */
public class OrderMother {

    private OrderMother() {
        // Utility class
    }

    public static Order createCompleteOrder() {
        Instant now = Instant.now();

        Order order = new Order();
        order.setId(new ObjectId().toHexString());
        order.setOrderId(System.currentTimeMillis());
        order.setCustomerId("customer-456");
        order.setOrderDate(now);
        order.setStatus(createRecebidoStatus());
        order.setQueuePosition(1);
        order.setItems(Arrays.asList(createDefaultItem()));
        order.setPayment(createPendentePayment());
        order.setCreatedAt(now);
        order.setUpdatedAt(now);

        return order;
    }

    public static Order createOrderWithMercadoPago() {
        Order order = createCompleteOrder();
        Order.Payment payment = order.getPayment();
        payment.setMercadoPagoInfo(createMercadoPagoInfo());
        return order;
    }

    public static Order createMinimalOrder() {
        Order order = new Order();
        order.setOrderId(System.currentTimeMillis());
        order.setCustomerId("customer-minimal");
        return order;
    }

    public static Order.Status createRecebidoStatus() {
        Order.Status status = new Order.Status();
        status.setId(1L);
        status.setName(StatusPedidoEnum.RECEBIDO.getStatus());
        status.setUpdatedAt(Instant.now());
        return status;
    }

    public static Order.Status createEmAndamentoStatus() {
        Order.Status status = new Order.Status();
        status.setId(2L);
        status.setName(StatusPedidoEnum.EM_ANDAMENTO.getStatus());
        status.setUpdatedAt(Instant.now());
        return status;
    }

    public static Order.Status createFinalizadoStatus() {
        Order.Status status = new Order.Status();
        status.setId(3L);
        status.setName(StatusPedidoEnum.FINALIZADO.getStatus());
        status.setUpdatedAt(Instant.now());
        return status;
    }

    public static Order.Item createDefaultItem() {
        Order.Item item = new Order.Item();
        item.setProductId(1L);
        item.setQuantity(2);
        item.setUnitPrice(BigDecimal.valueOf(50.00));
        item.setSubtotal(BigDecimal.valueOf(100.00));
        return item;
    }

    public static Order.Item createCustomItem(Long productId, Integer quantity, BigDecimal unitPrice) {
        Order.Item item = new Order.Item();
        item.setProductId(productId);
        item.setQuantity(quantity);
        item.setUnitPrice(unitPrice);
        item.setSubtotal(unitPrice.multiply(new BigDecimal(quantity)));
        return item;
    }

    public static Order.Payment createPendentePayment() {
        Order.Payment payment = new Order.Payment();
        payment.setPaymentId(System.currentTimeMillis());
        payment.setTotalAmount(BigDecimal.valueOf(100.00));
        payment.setPaymentMethod("PIX");
        payment.setStatus(createPendentePaymentStatus());
        payment.setPaymentDate(Instant.now());
        return payment;
    }

    public static Order.Payment createFinalizadoPayment() {
        Order.Payment payment = createPendentePayment();
        payment.setStatus(createFinalizadoPaymentStatus());
        return payment;
    }

    public static Order.Payment.PaymentStatus createPendentePaymentStatus() {
        Order.Payment.PaymentStatus status = new Order.Payment.PaymentStatus();
        status.setId(1L);
        status.setName(StatusPagamentoEnum.PENDENTE.getStatus());
        return status;
    }

    public static Order.Payment.PaymentStatus createFinalizadoPaymentStatus() {
        Order.Payment.PaymentStatus status = new Order.Payment.PaymentStatus();
        status.setId(2L);
        status.setName(StatusPagamentoEnum.FINALIZADO.getStatus());
        return status;
    }

    public static Order.MercadoPagoInfo createMercadoPagoInfo() {
        Order.MercadoPagoInfo info = new Order.MercadoPagoInfo();
        info.setOrderId("mp-order-123");
        info.setStatus("approved");
        info.setStatusDetail("accredited");
        info.setExternalReference("ext-ref-456");
        info.setQrCode("qr-code-data");
        info.setQrCodeBase64("qr-base64-data");
        info.setTicketUrl("http://ticket.url");
        return info;
    }

    public static CheckoutRequest createCheckoutRequest() {
        CheckoutRequest request = new CheckoutRequest();
        request.setIdCliente("customer-123");
        request.setMetodoPagamento("PIX");

        CheckoutRequest.ItemProduto item = new CheckoutRequest.ItemProduto();
        item.setIdProduto(1L);
        item.setQuantidade(2);
        item.setPrecoUnitario(BigDecimal.valueOf(50.00));

        request.setProdutos(Collections.singletonList(item));
        return request;
    }

    public static CheckoutRequest createCheckoutRequestWithMultipleItems() {
        CheckoutRequest request = new CheckoutRequest();
        request.setIdCliente("customer-456");
        request.setMetodoPagamento("CARTAO");

        CheckoutRequest.ItemProduto item1 = new CheckoutRequest.ItemProduto();
        item1.setIdProduto(1L);
        item1.setQuantidade(2);
        item1.setPrecoUnitario(BigDecimal.valueOf(50.00));

        CheckoutRequest.ItemProduto item2 = new CheckoutRequest.ItemProduto();
        item2.setIdProduto(2L);
        item2.setQuantidade(1);
        item2.setPrecoUnitario(BigDecimal.valueOf(100.00));

        request.setProdutos(Arrays.asList(item1, item2));
        return request;
    }
}


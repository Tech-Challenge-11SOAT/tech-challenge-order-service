package br.com.postech.techchallange_order.application.services;

import br.com.postech.techchallange_order.domain.model.Order;
import br.com.postech.techchallange_order.domain.ports.out.CustomerServicePort;
import br.com.postech.techchallange_order.domain.ports.out.PaymentGatewayPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MercadoPagoServiceTest {

    @Mock
    private PaymentGatewayPort paymentGateway;

    @Mock
    private CustomerServicePort customerService;

    @InjectMocks
    private MercadoPagoService mercadoPagoService;

    private Order order;
    private Order updatedOrder;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setId("order-123");
        order.setCustomerId("customer-456");

        updatedOrder = new Order();
        updatedOrder.setId("order-123");
        updatedOrder.setCustomerId("customer-456");

        Order.Payment payment = new Order.Payment();
        Order.MercadoPagoInfo mpInfo = new Order.MercadoPagoInfo();
        mpInfo.setQrCode("qr-code");
        mpInfo.setQrCodeBase64("qr-base64");
        payment.setMercadoPagoInfo(mpInfo);
        updatedOrder.setPayment(payment);
    }

    @Test
    void shouldProcessPaymentWithMercadoPagoSuccessfully() {
        String customerEmail = "customer@example.com";
        when(customerService.getEmailByCustomerId("customer-456")).thenReturn(customerEmail);
        when(paymentGateway.createPaymentOrder(any(Order.class), eq(customerEmail))).thenReturn(updatedOrder);

        Order result = mercadoPagoService.processPaymentWithMercadoPago(order);

        assertNotNull(result);
        assertEquals("order-123", result.getId());
        assertNotNull(result.getPayment());
        assertNotNull(result.getPayment().getMercadoPagoInfo());
        assertEquals("qr-code", result.getPayment().getMercadoPagoInfo().getQrCode());

        verify(customerService, times(1)).getEmailByCustomerId("customer-456");
        verify(paymentGateway, times(1)).createPaymentOrder(order, customerEmail);
    }

    @Test
    void shouldProcessPaymentWithDifferentCustomerId() {
        order.setCustomerId("customer-789");
        String customerEmail = "another@example.com";

        when(customerService.getEmailByCustomerId("customer-789")).thenReturn(customerEmail);
        when(paymentGateway.createPaymentOrder(any(Order.class), eq(customerEmail))).thenReturn(updatedOrder);

        Order result = mercadoPagoService.processPaymentWithMercadoPago(order);

        assertNotNull(result);

        verify(customerService, times(1)).getEmailByCustomerId("customer-789");
        verify(paymentGateway, times(1)).createPaymentOrder(order, customerEmail);
    }

    @Test
    void shouldHandleNullCustomerId() {
        order.setCustomerId(null);
        when(customerService.getEmailByCustomerId(null)).thenReturn(null);
        when(paymentGateway.createPaymentOrder(any(Order.class), isNull())).thenReturn(updatedOrder);

        Order result = mercadoPagoService.processPaymentWithMercadoPago(order);

        assertNotNull(result);

        verify(customerService, times(1)).getEmailByCustomerId(null);
        verify(paymentGateway, times(1)).createPaymentOrder(order, null);
    }

    @Test
    void shouldHandleEmptyEmail() {
        when(customerService.getEmailByCustomerId("customer-456")).thenReturn("");
        when(paymentGateway.createPaymentOrder(any(Order.class), eq(""))).thenReturn(updatedOrder);

        Order result = mercadoPagoService.processPaymentWithMercadoPago(order);

        assertNotNull(result);

        verify(customerService, times(1)).getEmailByCustomerId("customer-456");
        verify(paymentGateway, times(1)).createPaymentOrder(order, "");
    }
}


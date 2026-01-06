package br.com.postech.techchallange_order.infrastructure.adapters.in.rest;

import br.com.postech.techchallange_order.domain.ports.in.CheckouUseCase;
import br.com.postech.techchallange_order.infrastructure.adapters.in.rest.dto.CheckoutRequest;
import br.com.postech.techchallange_order.infrastructure.adapters.in.rest.dto.response.CheckoutResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CheckoutController.class)
class CheckoutControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CheckouUseCase checkoutUseCase;

    private CheckoutRequest checkoutRequest;
    private CheckoutResponse checkoutResponse;

    @BeforeEach
    void setUp() {
        checkoutRequest = new CheckoutRequest();
        checkoutRequest.setIdCliente("customer-123");
        checkoutRequest.setMetodoPagamento("PIX");

        CheckoutRequest.ItemProduto item = new CheckoutRequest.ItemProduto();
        item.setIdProduto(1L);
        item.setQuantidade(2);
        item.setPrecoUnitario(BigDecimal.valueOf(50));
        checkoutRequest.setProdutos(Arrays.asList(item));

        checkoutResponse = new CheckoutResponse();
        checkoutResponse.setIdPedido("order-123");
        checkoutResponse.setStatus("RECEBIDO");
        checkoutResponse.setQrCode("qr-code-data");
        checkoutResponse.setQrCodeBase64("qr-base64-data");
    }

    @Test
    void shouldProcessCheckoutSuccessfully() throws Exception {
        when(checkoutUseCase.processarCheckout(any(CheckoutRequest.class))).thenReturn(checkoutResponse);

        mockMvc.perform(post("/api/checkout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(checkoutRequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.idPedido").value("order-123"))
            .andExpect(jsonPath("$.status").value("RECEBIDO"))
            .andExpect(jsonPath("$.qrCode").value("qr-code-data"))
            .andExpect(jsonPath("$.qrCodeBase64").value("qr-base64-data"));

        verify(checkoutUseCase, times(1)).processarCheckout(any(CheckoutRequest.class));
    }

    @Test
    void shouldReturnBadRequestWhenProductsListIsNull() throws Exception {
        checkoutRequest.setProdutos(null);

        mockMvc.perform(post("/api/checkout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(checkoutRequest)))
            .andExpect(status().isBadRequest());

        verify(checkoutUseCase, never()).processarCheckout(any(CheckoutRequest.class));
    }

    @Test
    void shouldProcessCheckoutWithMultipleItems() throws Exception {
        CheckoutRequest.ItemProduto item1 = new CheckoutRequest.ItemProduto();
        item1.setIdProduto(1L);
        item1.setQuantidade(2);
        item1.setPrecoUnitario(BigDecimal.valueOf(50));

        CheckoutRequest.ItemProduto item2 = new CheckoutRequest.ItemProduto();
        item2.setIdProduto(2L);
        item2.setQuantidade(1);
        item2.setPrecoUnitario(BigDecimal.valueOf(100));

        checkoutRequest.setProdutos(Arrays.asList(item1, item2));

        when(checkoutUseCase.processarCheckout(any(CheckoutRequest.class))).thenReturn(checkoutResponse);

        mockMvc.perform(post("/api/checkout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(checkoutRequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.idPedido").value("order-123"));

        verify(checkoutUseCase, times(1)).processarCheckout(any(CheckoutRequest.class));
    }

    @Test
    void shouldProcessCheckoutWithDifferentPaymentMethods() throws Exception {
        when(checkoutUseCase.processarCheckout(any(CheckoutRequest.class))).thenReturn(checkoutResponse);

        checkoutRequest.setMetodoPagamento("CARTAO");
        mockMvc.perform(post("/api/checkout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(checkoutRequest)))
            .andExpect(status().isOk());

        checkoutRequest.setMetodoPagamento("DINHEIRO");
        mockMvc.perform(post("/api/checkout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(checkoutRequest)))
            .andExpect(status().isOk());

        verify(checkoutUseCase, times(2)).processarCheckout(any(CheckoutRequest.class));
    }

    @Test
    void shouldProcessCheckoutWithNullCustomerId() throws Exception {
        checkoutRequest.setIdCliente(null);

        when(checkoutUseCase.processarCheckout(any(CheckoutRequest.class))).thenReturn(checkoutResponse);

        mockMvc.perform(post("/api/checkout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(checkoutRequest)))
            .andExpect(status().isOk());

        verify(checkoutUseCase, times(1)).processarCheckout(any(CheckoutRequest.class));
    }
}


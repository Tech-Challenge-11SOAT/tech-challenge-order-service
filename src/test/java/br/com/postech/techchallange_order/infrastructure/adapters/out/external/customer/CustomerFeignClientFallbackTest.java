package br.com.postech.techchallange_order.infrastructure.adapters.out.external.customer;

import br.com.postech.techchallange_order.infrastructure.adapters.out.external.customer.dto.CustomerResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerFeignClientFallbackTest {

    private CustomerFeignClientFallback fallback;

    @BeforeEach
    void setUp() {
        fallback = new CustomerFeignClientFallback();
    }

    @Test
    void shouldReturnFallbackCustomerWhenCalled() {
        String customerId = "customer-123";

        CustomerResponseDTO result = fallback.getCustomerById(customerId);

        assertNotNull(result);
        assertEquals("anon@anon.com", result.getEmailCliente());
        assertEquals("Cliente Anônimo", result.getNomeCliente());
        assertEquals(customerId, result.getClienteId());
    }

    @Test
    void shouldReturnFallbackForNullCustomerId() {
        CustomerResponseDTO result = fallback.getCustomerById(null);

        assertNotNull(result);
        assertEquals("anon@anon.com", result.getEmailCliente());
        assertEquals("Cliente Anônimo", result.getNomeCliente());
        assertNull(result.getClienteId());
    }

    @Test
    void shouldReturnFallbackForDifferentCustomerIds() {
        CustomerResponseDTO result1 = fallback.getCustomerById("customer-1");
        CustomerResponseDTO result2 = fallback.getCustomerById("customer-2");

        assertNotNull(result1);
        assertNotNull(result2);
        assertEquals("customer-1", result1.getClienteId());
        assertEquals("customer-2", result2.getClienteId());
        assertEquals(result1.getEmailCliente(), result2.getEmailCliente());
        assertEquals(result1.getNomeCliente(), result2.getNomeCliente());
    }
}


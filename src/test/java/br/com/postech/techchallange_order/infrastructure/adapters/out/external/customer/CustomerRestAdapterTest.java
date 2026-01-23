package br.com.postech.techchallange_order.infrastructure.adapters.out.external.customer;

import br.com.postech.techchallange_order.infrastructure.adapters.out.external.customer.dto.CustomerResponseDTO;
import br.com.postech.techchallange_order.infrastructure.adapters.out.external.mercadopago.MercadoPagoConstants;
import br.com.postech.techchallange_order.infrastructure.config.MercadoPagoOptionsConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerRestAdapterTest {

    @Mock
    private CustomerFeignClient customerFeignClient;

    @Mock
    private MercadoPagoOptionsConfig mercadoPagoOptionsConfig;

    private CustomerRestAdapter customerRestAdapter;

    @BeforeEach
    void setUp() {
        customerRestAdapter = new CustomerRestAdapter(customerFeignClient, mercadoPagoOptionsConfig);
    }

    @Test
    void shouldReturnTestEmailWhenTestModeIsTrue() {
        MercadoPagoOptionsConfig.Options options = new MercadoPagoOptionsConfig.Options();
        options.setTestMode(true);
        when(mercadoPagoOptionsConfig.getOptions()).thenReturn(options);

        String email = customerRestAdapter.getEmailByCustomerId("customer-123");

        assertEquals(MercadoPagoConstants.MERCADO_PAGO_TEST_EMAIL, email);
        verify(customerFeignClient, never()).getCustomerById(anyString());
    }

    @Test
    void shouldReturnTestEmailWhenCustomerIdIsNull() {
        MercadoPagoOptionsConfig.Options options = new MercadoPagoOptionsConfig.Options();
        options.setTestMode(false);
        when(mercadoPagoOptionsConfig.getOptions()).thenReturn(options);

        String email = customerRestAdapter.getEmailByCustomerId(null);

        assertEquals(MercadoPagoConstants.MERCADO_PAGO_TEST_EMAIL, email);
        verify(customerFeignClient, never()).getCustomerById(anyString());
    }

    @Test
    void shouldReturnCustomerEmailFromFeignClient() {
        MercadoPagoOptionsConfig.Options options = new MercadoPagoOptionsConfig.Options();
        options.setTestMode(false);
        when(mercadoPagoOptionsConfig.getOptions()).thenReturn(options);

        CustomerResponseDTO customerResponse = new CustomerResponseDTO();
        customerResponse.setEmailCliente("customer@email.com");
        when(customerFeignClient.getCustomerById("customer-123")).thenReturn(customerResponse);

        String email = customerRestAdapter.getEmailByCustomerId("customer-123");

        assertEquals("customer@email.com", email);
        verify(customerFeignClient, times(1)).getCustomerById("customer-123");
    }

    @Test
    void shouldReturnTestEmailWhenCustomerResponseIsNull() {
        MercadoPagoOptionsConfig.Options options = new MercadoPagoOptionsConfig.Options();
        options.setTestMode(false);
        when(mercadoPagoOptionsConfig.getOptions()).thenReturn(options);
        when(customerFeignClient.getCustomerById("customer-123")).thenReturn(null);

        String email = customerRestAdapter.getEmailByCustomerId("customer-123");

        assertEquals(MercadoPagoConstants.MERCADO_PAGO_TEST_EMAIL, email);
        verify(customerFeignClient, times(1)).getCustomerById("customer-123");
    }

    @Test
    void shouldReturnTestEmailWhenCustomerEmailIsNull() {
        MercadoPagoOptionsConfig.Options options = new MercadoPagoOptionsConfig.Options();
        options.setTestMode(false);
        when(mercadoPagoOptionsConfig.getOptions()).thenReturn(options);

        CustomerResponseDTO customerResponse = new CustomerResponseDTO();
        customerResponse.setEmailCliente(null);
        when(customerFeignClient.getCustomerById("customer-123")).thenReturn(customerResponse);

        String email = customerRestAdapter.getEmailByCustomerId("customer-123");

        assertEquals(MercadoPagoConstants.MERCADO_PAGO_TEST_EMAIL, email);
        verify(customerFeignClient, times(1)).getCustomerById("customer-123");
    }
}


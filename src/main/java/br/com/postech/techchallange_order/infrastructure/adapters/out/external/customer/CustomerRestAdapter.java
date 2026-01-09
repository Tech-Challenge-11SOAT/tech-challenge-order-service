package br.com.postech.techchallange_order.infrastructure.adapters.out.external.customer;

import org.springframework.stereotype.Component;

import br.com.postech.techchallange_order.domain.ports.out.CustomerServicePort;
import br.com.postech.techchallange_order.infrastructure.adapters.out.external.customer.dto.CustomerResponseDTO;
import br.com.postech.techchallange_order.infrastructure.adapters.out.external.mercadopago.MercadoPagoConstants;
import br.com.postech.techchallange_order.infrastructure.config.MercadoPagoOptionsConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerRestAdapter implements CustomerServicePort {

	private final CustomerFeignClient customerFeignClient;
	private final MercadoPagoOptionsConfig mercadoPagoOptionsConfig;

	@Override
	public String getEmailByCustomerId(String customerId) {
		if (Boolean.TRUE.equals(mercadoPagoOptionsConfig.getOptions().getTestMode()) || customerId == null) {
			log.info("ID do cliente é nulo, usando e-mail anônimo");
			return MercadoPagoConstants.MERCADO_PAGO_TEST_EMAIL;
		}

		log.info("Buscando e-mail para cliente ID {} via Feign", customerId);
		CustomerResponseDTO customer = customerFeignClient.getCustomerById(customerId);
		return customer != null && customer.getEmailCliente() != null
				? customer.getEmailCliente()
				: MercadoPagoConstants.MERCADO_PAGO_TEST_EMAIL;
	}
}

package br.com.postech.techchallange_order.infrastructure.adapters.out.external.customer;

import org.springframework.stereotype.Component;

import br.com.postech.techchallange_order.infrastructure.adapters.out.external.customer.dto.CustomerResponseDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomerFeignClientFallback implements CustomerFeignClient {

	@Override
	public CustomerResponseDTO getCustomerById(String customerId) {
		log.warn("Fallback do serviço de cliente ativado para ID: {}", customerId);
		CustomerResponseDTO fallbackCustomer = new CustomerResponseDTO();
		fallbackCustomer.setEmailCliente("anon@anon.com");
		fallbackCustomer.setNomeCliente("Cliente Anônimo");
		fallbackCustomer.setClienteId(customerId);
		return fallbackCustomer;
	}
}

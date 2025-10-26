package br.com.postech.techchallange_order.infrastructure.adapters.out.external.customer;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomerFeignClientFallback implements CustomerFeignClient {

	@Override
	public String getEmailByCustomerId(Long customerId) {
		log.warn("Fallback do serviço de cliente ativado para ID: {}", customerId);
		return "anon@anon.com";
	}
}

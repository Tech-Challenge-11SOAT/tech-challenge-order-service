package br.com.postech.techchallange_order.infrastructure.adapters.out.external.customer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.postech.techchallange_order.infrastructure.adapters.out.external.customer.dto.CustomerResponseDTO;

@FeignClient(name = "customer-service", url = "${customer.service.url}", fallback = CustomerFeignClientFallback.class)
public interface CustomerFeignClient {

	@GetMapping("/api/v1/clientes/{customerId}")
	CustomerResponseDTO getCustomerById(@PathVariable("customerId") String customerId);
}

package br.com.postech.techchallange_order.infrastructure.adapters.out.external.customer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service", url = "${customer.service.url}", fallback = CustomerFeignClientFallback.class)
public interface CustomerFeignClient {

	@GetMapping("/api/customers/{customerId}/email")
	String getEmailByCustomerId(@PathVariable("customerId") Long customerId);
}

package br.com.postech.techchallange_order.domain.ports.out;

public interface CustomerServicePort {
	String getEmailByCustomerId(Long customerId);
}

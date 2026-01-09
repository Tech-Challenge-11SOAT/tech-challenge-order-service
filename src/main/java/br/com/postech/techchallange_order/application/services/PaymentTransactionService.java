package br.com.postech.techchallange_order.application.services;

import org.springframework.stereotype.Service;

import br.com.postech.techchallange_order.domain.model.PaymentTransaction;
import br.com.postech.techchallange_order.domain.ports.in.PaymentTransactionUseCase;
import br.com.postech.techchallange_order.infrastructure.adapters.out.mongodb.mapper.PaymentTransactionMapper;
import br.com.postech.techchallange_order.infrastructure.adapters.out.mongodb.model.PaymentTransactionDocument;
import br.com.postech.techchallange_order.infrastructure.adapters.out.persistence.PaymentTransactionMongoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentTransactionService implements PaymentTransactionUseCase {

	private final PaymentTransactionMapper paymentTransactionMapper;
	private final PaymentTransactionMongoRepository paymentTransactionRepository;

	@Override
	public void createPaymentTransaction(PaymentTransaction transaction) {
		PaymentTransactionDocument doc = paymentTransactionMapper.toDocument(transaction);
		this.paymentTransactionRepository.save(doc);
	}

}

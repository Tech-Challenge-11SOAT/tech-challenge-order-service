package br.com.postech.techchallange_order.application.services;

import java.time.Instant;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import br.com.postech.techchallange_order.domain.enums.StatusPagamentoEnum;
import br.com.postech.techchallange_order.domain.model.OrderQueueItem;
import br.com.postech.techchallange_order.domain.model.PaymentTransaction;
import br.com.postech.techchallange_order.domain.ports.in.PaymentTransactionUseCase;
import br.com.postech.techchallange_order.domain.ports.in.PaymentUseCase;
import br.com.postech.techchallange_order.domain.ports.out.QueueRepositoryPort;
import br.com.postech.techchallange_order.infrastructure.adapters.out.mongodb.model.OrderDocument;
import br.com.postech.techchallange_order.infrastructure.adapters.out.persistence.OrderMongoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService implements PaymentUseCase {

	private final PaymentTransactionUseCase paymentTransactionUseCase;
	private final OrderMongoRepository orderMongoRepository; // used to read order and customerId
	private final QueueRepositoryPort queueRepository;

	@Override
	public void processPaymentTransaction(PaymentTransaction transaction) {
		this.paymentTransactionUseCase.createPaymentTransaction(transaction);

		// only create queue when payment is FINALIZADO
		if (transaction.getStatus() != null
				&& StatusPagamentoEnum.FINALIZADO.getStatus().equalsIgnoreCase(transaction.getStatus())) {

			String customerId = null;
			try {
				if (transaction.getOrderId() != null) {
					ObjectId oid = new ObjectId(transaction.getOrderId());
					Optional<OrderDocument> maybe = orderMongoRepository.findById(oid);
					if (maybe.isPresent()) {
						customerId = maybe.get().getCustomerId();
					}
				}
			} catch (Exception e) {
				// ignore lookup errors; queue can be created with null customerId
			}

			OrderQueueItem item = new OrderQueueItem();
			item.setOrderId(transaction.getOrderId());
			item.setCustomerId(customerId);
			item.setCreatedAt(Instant.now());
			item.setStatus("QUEUED");

			queueRepository.save(item);
		}
	}
}

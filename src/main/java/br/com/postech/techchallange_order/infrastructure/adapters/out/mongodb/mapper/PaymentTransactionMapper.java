package br.com.postech.techchallange_order.infrastructure.adapters.out.mongodb.mapper;

import org.bson.types.Decimal128;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import br.com.postech.techchallange_order.domain.model.PaymentTransaction;
import br.com.postech.techchallange_order.infrastructure.adapters.out.mongodb.model.PaymentTransactionDocument;

@Component
public class PaymentTransactionMapper {

	public PaymentTransactionDocument toDocument(PaymentTransaction paymentTransaction) {
		if (paymentTransaction == null)
			return null;

		ObjectId id = paymentTransaction.getId() != null ? new ObjectId(paymentTransaction.getId()) : null;
		ObjectId orderId = paymentTransaction.getOrderId() != null ? new ObjectId(paymentTransaction.getOrderId())
				: null;

		Decimal128 amount = paymentTransaction.getAmount() != null ? new Decimal128(paymentTransaction.getAmount())
				: null;

		return PaymentTransactionDocument.builder()
				.id(id)
				.orderId(orderId)
				.transactionId(paymentTransaction.getTransactionId())
				.amount(amount)
				.paymentMethod(paymentTransaction.getPaymentMethod())
				.status(paymentTransaction.getStatus())
				.gatewayResponse(paymentTransaction.getGatewayResponse())
				.createdAt(paymentTransaction.getCreatedAt())
				.updatedAt(paymentTransaction.getUpdatedAt())
				.build();
	}

	public PaymentTransaction toDomain(PaymentTransactionDocument document) {
		if (document == null)
			return null;

		String id = document.getId() != null ? document.getId().toHexString() : null;
		String orderId = document.getOrderId() != null ? document.getOrderId().toHexString() : null;

		java.math.BigDecimal amount = document.getAmount() != null ? document.getAmount().bigDecimalValue() : null;

		PaymentTransaction pt = new PaymentTransaction();
		pt.setId(id);
		pt.setOrderId(orderId);
		pt.setTransactionId(document.getTransactionId());
		pt.setAmount(amount);
		pt.setPaymentMethod(document.getPaymentMethod());
		pt.setStatus(document.getStatus());
		pt.setGatewayResponse(document.getGatewayResponse());
		pt.setCreatedAt(document.getCreatedAt());
		pt.setUpdatedAt(document.getUpdatedAt());

		return pt;
	}
}
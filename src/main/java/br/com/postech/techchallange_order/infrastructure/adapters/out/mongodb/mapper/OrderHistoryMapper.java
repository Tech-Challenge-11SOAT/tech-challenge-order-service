package br.com.postech.techchallange_order.infrastructure.adapters.out.mongodb.mapper;

import org.bson.types.ObjectId;

import br.com.postech.techchallange_order.domain.model.Order;
import br.com.postech.techchallange_order.infrastructure.adapters.out.mongodb.model.OrderHistoryDocument;

public final class OrderHistoryMapper {

	private OrderHistoryMapper() {
	}

	public static OrderHistoryDocument toDocument(Order domain, Order.Status previousStatus) {
		if (domain == null)
			return null;

		OrderHistoryDocument doc = new OrderHistoryDocument();
		if (domain.getId() != null)
			doc.setOrderId(new ObjectId(domain.getId()));

		if (domain.getStatus() != null) {
			OrderHistoryDocument.Status s = new OrderHistoryDocument.Status();
			s.setId(domain.getStatus().getId());
			s.setName(domain.getStatus().getName());
			doc.setStatus(s);
		}

		if (previousStatus != null) {
			OrderHistoryDocument.Status ps = new OrderHistoryDocument.Status();
			ps.setId(previousStatus.getId());
			ps.setName(previousStatus.getName());
			doc.setPreviousStatus(ps);
		}
		return doc;
	}
}

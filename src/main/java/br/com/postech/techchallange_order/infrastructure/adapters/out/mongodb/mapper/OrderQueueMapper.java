package br.com.postech.techchallange_order.infrastructure.adapters.out.mongodb.mapper;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import br.com.postech.techchallange_order.domain.model.OrderQueueItem;
import br.com.postech.techchallange_order.infrastructure.adapters.out.mongodb.model.OrderQueueDocument;

@Component
public class OrderQueueMapper {

	public OrderQueueDocument toDocument(OrderQueueItem item) {
		if (item == null)
			return null;
		ObjectId orderId = item.getOrderId() != null ? new ObjectId(item.getOrderId()) : null;
		ObjectId id = item.getId() != null ? new ObjectId(item.getId()) : null;

		OrderQueueDocument doc = new OrderQueueDocument();
		doc.setId(id);
		doc.setOrderId(orderId);
		doc.setCustomerId(item.getCustomerId());
		doc.setCreatedAt(item.getCreatedAt());
		doc.setStatus(item.getStatus());
		return doc;
	}

	public OrderQueueItem toDomain(OrderQueueDocument doc) {
		if (doc == null)
			return null;
		OrderQueueItem item = new OrderQueueItem();
		item.setId(doc.getId() != null ? doc.getId().toHexString() : null);
		item.setOrderId(doc.getOrderId() != null ? doc.getOrderId().toHexString() : null);
		item.setCustomerId(doc.getCustomerId());
		item.setCreatedAt(doc.getCreatedAt());
		item.setStatus(doc.getStatus());
		return item;
	}
}

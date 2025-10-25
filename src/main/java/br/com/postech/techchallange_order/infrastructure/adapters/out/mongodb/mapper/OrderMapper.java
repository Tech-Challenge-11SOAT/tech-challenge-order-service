package br.com.postech.techchallange_order.infrastructure.adapters.out.mongodb.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.Decimal128;
import org.bson.types.ObjectId;

import br.com.postech.techchallange_order.domain.model.Order;
import br.com.postech.techchallange_order.infrastructure.adapters.out.mongodb.model.OrderDocument;

public final class OrderMapper {

	private OrderMapper() {
	}

	public static OrderDocument toDocument(Order domain) {
		if (domain == null)
			return null;

		OrderDocument doc = new OrderDocument();
		if (domain.getId() != null)
			doc.setId(new ObjectId(domain.getId()));

		doc.setOrderId(domain.getOrderId());
		doc.setCustomerId(domain.getCustomerId());
		doc.setOrderDate(domain.getOrderDate());

		if (domain.getStatus() != null) {
			OrderDocument.Status s = new OrderDocument.Status();
			s.setId(domain.getStatus().getId());
			s.setName(domain.getStatus().getName());
			s.setUpdatedAt(domain.getStatus().getUpdatedAt());
			doc.setStatus(s);
		}

		doc.setQueuePosition(domain.getQueuePosition());
		if (domain.getItems() != null) {
			List<OrderDocument.Item> items = domain.getItems().stream().map(i -> {
				OrderDocument.Item it = new OrderDocument.Item();
				it.setProductId(i.getProductId());
				it.setQuantity(i.getQuantity());
				it.setUnitPrice(i.getUnitPrice() != null ? new Decimal128(i.getUnitPrice()) : null);
				it.setSubtotal(i.getSubtotal() != null ? new Decimal128(i.getSubtotal()) : null);
				return it;
			}).collect(Collectors.toList());
			doc.setItems(items);
		}

		if (domain.getPayment() != null) {
			OrderDocument.Payment p = new OrderDocument.Payment();
			p.setPaymentId(domain.getPayment().getPaymentId());
			p.setTotalAmount(
					domain.getPayment().getTotalAmount() != null ? new Decimal128(domain.getPayment().getTotalAmount())
							: null);
			p.setPaymentMethod(domain.getPayment().getPaymentMethod());
			if (domain.getPayment().getStatus() != null) {
				OrderDocument.PaymentStatus ps = new OrderDocument.PaymentStatus();
				ps.setId(domain.getPayment().getStatus().getId());
				ps.setName(domain.getPayment().getStatus().getName());
				p.setStatus(ps);
			}

			p.setPaymentDate(domain.getPayment().getPaymentDate());
			if (domain.getPayment().getMercadoPagoInfo() != null) {
				OrderDocument.MercadoPagoInfo mpi = new OrderDocument.MercadoPagoInfo();
				mpi.setOrderId(domain.getPayment().getMercadoPagoInfo().getOrderId());
				mpi.setStatus(domain.getPayment().getMercadoPagoInfo().getStatus());
				mpi.setStatusDetail(domain.getPayment().getMercadoPagoInfo().getStatusDetail());
				mpi.setExternalReference(domain.getPayment().getMercadoPagoInfo().getExternalReference());
				mpi.setQrCode(domain.getPayment().getMercadoPagoInfo().getQrCode());
				mpi.setQrCodeBase64(domain.getPayment().getMercadoPagoInfo().getQrCodeBase64());
				mpi.setTicketUrl(domain.getPayment().getMercadoPagoInfo().getTicketUrl());
				p.setMercadoPagoInfo(mpi);
			}
			doc.setPayment(p);
		}

		doc.setCreatedAt(domain.getCreatedAt());
		doc.setUpdatedAt(domain.getUpdatedAt());
		return doc;
	}

	public static Order toDomain(OrderDocument doc) {
		if (doc == null)
			return null;

		Order domain = new Order();
		if (doc.getId() != null)
			domain.setId(doc.getId().toHexString());

		domain.setOrderId(doc.getOrderId());
		domain.setCustomerId(doc.getCustomerId());
		domain.setOrderDate(doc.getOrderDate());

		if (doc.getStatus() != null) {
			Order.Status s = new Order.Status();
			s.setId(doc.getStatus().getId());
			s.setName(doc.getStatus().getName());
			s.setUpdatedAt(doc.getStatus().getUpdatedAt());
			domain.setStatus(s);
		}

		domain.setQueuePosition(doc.getQueuePosition());
		if (doc.getItems() != null) {
			List<Order.Item> items = doc.getItems().stream().map(i -> {
				Order.Item it = new Order.Item();
				it.setProductId(i.getProductId());
				it.setQuantity(i.getQuantity());
				it.setUnitPrice(i.getUnitPrice() != null ? i.getUnitPrice().bigDecimalValue() : null);
				it.setSubtotal(i.getSubtotal() != null ? i.getSubtotal().bigDecimalValue() : null);
				return it;
			}).collect(Collectors.toList());
			domain.setItems(items);
		}

		if (doc.getPayment() != null) {
			Order.Payment p = new Order.Payment();
			p.setPaymentId(doc.getPayment().getPaymentId());
			p.setTotalAmount(
					doc.getPayment().getTotalAmount() != null ? doc.getPayment().getTotalAmount().bigDecimalValue()
							: null);
			p.setPaymentMethod(doc.getPayment().getPaymentMethod());
			if (doc.getPayment().getStatus() != null) {
				Order.Payment.PaymentStatus ps = new Order.Payment.PaymentStatus();
				ps.setId(doc.getPayment().getStatus().getId());
				ps.setName(doc.getPayment().getStatus().getName());
				p.setStatus(ps);
			}

			p.setPaymentDate(doc.getPayment().getPaymentDate());
			if (doc.getPayment().getMercadoPagoInfo() != null) {
				Order.MercadoPagoInfo mpi = new Order.MercadoPagoInfo();
				mpi.setOrderId(doc.getPayment().getMercadoPagoInfo().getOrderId());
				mpi.setStatus(doc.getPayment().getMercadoPagoInfo().getStatus());
				mpi.setStatusDetail(doc.getPayment().getMercadoPagoInfo().getStatusDetail());
				mpi.setExternalReference(doc.getPayment().getMercadoPagoInfo().getExternalReference());
				mpi.setQrCode(doc.getPayment().getMercadoPagoInfo().getQrCode());
				mpi.setQrCodeBase64(doc.getPayment().getMercadoPagoInfo().getQrCodeBase64());
				mpi.setTicketUrl(doc.getPayment().getMercadoPagoInfo().getTicketUrl());
				p.setMercadoPagoInfo(mpi);
			}
			domain.setPayment(p);
		}

		domain.setCreatedAt(doc.getCreatedAt());
		domain.setUpdatedAt(doc.getUpdatedAt());

		return domain;
	}
}

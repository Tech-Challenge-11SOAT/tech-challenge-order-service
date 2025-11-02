package br.com.postech.techchallange_order.infrastructure.adapters.out.persistence;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.postech.techchallange_order.infrastructure.adapters.out.mongodb.model.OrderDocument;

@Repository
public interface MongoOrderRepository extends MongoRepository<OrderDocument, ObjectId> {
}
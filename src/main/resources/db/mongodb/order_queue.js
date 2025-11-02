use("tc-order");

db.createCollection("order_queue", {
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: ["orderId", "customerId", "createdAt", "status"],
      properties: {
        _id: {
          bsonType: "objectId",
          description: "ID único da entrada na fila"
        },
        orderId: {
          bsonType: "objectId",
          description: "Referência ao _id do pedido"
        },
        customerId: {
          bsonType: "string",
          description: "ID do cliente associado"
        },
        createdAt: {
          bsonType: "date",
          description: "Data de criação (gerada automaticamente pelo Spring @CreatedDate)"
        },
        status: {
          bsonType: "string",
          description: "Status atual do pedido na fila"
        }
      }
    }
  },
  validationLevel: "strict",
  validationAction: "error"
});

print("✅ Collection 'order_queue' criada com sucesso!");

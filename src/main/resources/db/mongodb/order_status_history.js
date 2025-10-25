use("tc-order");

db.createCollection("order_status_history", {
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: ["orderId", "status"],
      properties: {
        _id: {
          bsonType: "objectId",
          description: "ID único do histórico",
        },
        orderId: {
          bsonType: "objectId",
          description: "Referência ao _id do pedido",
        },
        status: {
          bsonType: "object",
          required: ["name"],
          properties: {
            id: { bsonType: "long" },
            name: { bsonType: "string" },
          },
          description: "Status atual do pedido",
        },
        previousStatus: {
          bsonType: ["object", "null"],
          properties: {
            id: { bsonType: "long" },
            name: { bsonType: "string" },
          },
          description: "Status anterior do pedido (opcional)",
        },
        createdAt: {
          bsonType: "date",
          description: "Data de criação do histórico de status",
        },
      },
    },
  },
  validationLevel: "strict",
  validationAction: "error",
});

print("✅ Collection 'order_status_history' criada com sucesso!");

use("tc-order");

db.createCollection("orders", {
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: [
        "orderId",
        "customerId",
        "orderDate",
        "status",
        "items",
        "payment",
        "createdAt",
        "updatedAt",
      ],
      properties: {
        _id: {
          bsonType: "objectId",
          description: "ID único do pedido",
        },
        orderId: {
          bsonType: "long",
          description: "ID do pedido legado (para compatibilidade)",
        },
        customerId: {
          bsonType: "string",
          description: "ID do cliente",
        },
        orderDate: {
          bsonType: "date",
          description: "Data do pedido",
        },
        status: {
          bsonType: "object",
          required: ["id", "name", "updatedAt"],
          properties: {
            id: { bsonType: "long" },
            name: { bsonType: "string" },
            updatedAt: { bsonType: "date" },
          },
        },
        queuePosition: {
          bsonType: ["int", "null"],
          description: "Posição na fila (opcional)",
        },
        items: {
          bsonType: "array",
          minItems: 1,
          items: {
            bsonType: "object",
            required: ["productId", "quantity", "unitPrice", "subtotal"],
            properties: {
              productId: { bsonType: "long" },
              quantity: { bsonType: "int" },
              unitPrice: { bsonType: "decimal" },
              subtotal: { bsonType: "decimal" },
            },
          },
        },
        payment: {
          bsonType: "object",
          required: [
            "paymentId",
            "totalAmount",
            "paymentMethod",
            "status",
            "paymentDate",
          ],
          properties: {
            paymentId: { bsonType: "long" },
            totalAmount: { bsonType: "decimal" },
            paymentMethod: { bsonType: "string" },
            status: {
              bsonType: "object",
              required: ["id", "name"],
              properties: {
                id: { bsonType: "long" },
                name: { bsonType: "string" },
              },
            },
            paymentDate: { bsonType: "date" },
            mercadoPagoInfo: {
              bsonType: ["object", "null"],
              properties: {
                orderId: { bsonType: "string" },
                status: { bsonType: "string" },
                statusDetail: { bsonType: "string" },
                externalReference: { bsonType: "string" },
                qrCode: { bsonType: "string" },
                qrCodeBase64: { bsonType: "string" },
                ticketUrl: { bsonType: "string" },
              },
            },
          },
        },
        createdAt: {
          bsonType: "date",
          description: "Data de criação",
        },
        updatedAt: {
          bsonType: "date",
          description: "Data da última atualização",
        },
      },
    },
  },
  validationLevel: "strict",
  validationAction: "error",
});

print("✅ Collection 'orders' criada com sucesso!");

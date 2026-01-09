use("tc-order");

db.createCollection("payment_transactions", {
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: [
        "amount",
        "paymentMethod",
        "status",
        "createdAt"
      ],
      properties: {
        _id: {
          bsonType: "objectId",
          description: "ID único da transação",
        },
        orderId: {
          bsonType: "objectId",
          description: "Referência ao _id do pedido",
        },
        transactionId: {
          bsonType: "string",
          description: "ID da transação do gateway de pagamento",
        },
        amount: {
          bsonType: "decimal",
          description: "Valor da transação",
        },
        paymentMethod: {
          bsonType: "string",
          description: "Método de pagamento (ex: cartão, pix, etc)",
        },
        status: {
          bsonType: "string",
          description: "Status atual da transação",
        },
        gatewayResponse: {
          bsonType: "object",
          description: "Resposta completa retornada pelo gateway",
        },
        createdAt: {
          bsonType: "date",
          description: "Data de criação da transação",
        },
        updatedAt: {
          bsonType: "date",
          description: "Data da última atualização da transação",
        },
      },
    },
  },
  validationLevel: "strict",
  validationAction: "error",
});

print("✅ Collection 'payment_transactions' criada com sucesso!");

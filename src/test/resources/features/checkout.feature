# language: pt
Funcionalidade: Processar Checkout de Pedidos
  Como um cliente
  Quero realizar o checkout do meu pedido
  Para finalizar minha compra e receber as informações de pagamento

  Cenário: Checkout básico com produtos
    Dado que o sistema está disponível
    E existe um produto com ID "1" e preço "15.50"
    Quando eu adicionar "2" unidades do produto ao carrinho
    E realizar o checkout
    Então o pedido deve ser processado com sucesso

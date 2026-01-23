# language: pt
Funcionalidade: Gerenciar Status do Pedido
  Como um operador do sistema
  Quero validar os status dos pedidos
  Para garantir que o fluxo de status funciona corretamente

  Cenário: Validar enum de status do pedido
    Dado que existe um enum de status de pedido
    Quando consultar os status disponíveis
    Então deve existir o status "RECEBIDO"
    E deve existir o status "FINALIZADO"

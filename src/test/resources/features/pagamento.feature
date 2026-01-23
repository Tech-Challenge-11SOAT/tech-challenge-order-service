# language: pt
Funcionalidade: Processar Pagamentos
  Como um sistema de pagamento
  Quero validar transações de pagamento
  Para garantir que o fluxo de pagamento funciona corretamente

  Cenário: Validar estrutura de transação de pagamento
    Dado que o sistema de pagamento está disponível
    Quando criar uma nova transação de pagamento
    Então a transação deve ter todos os campos obrigatórios

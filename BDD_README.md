# Testes BDD - Tech Challenge Order Service

## 📋 Visão Geral

Este projeto implementa testes BDD (Behavior-Driven Development) usando **Cucumber** com integração ao Spring Boot. Os testes cobrem os principais fluxos de negócio do sistema de pedidos.

## 🎯 Funcionalidades Cobertas

### 1. Checkout de Pedidos (`checkout.feature`)
- ✅ Checkout bem-sucedido com cliente identificado
- ✅ Checkout sem cliente identificado (cliente anônimo)
- ✅ Validação de checkout sem produtos
- ✅ Checkout com múltiplos produtos

### 2. Processamento de Pagamentos (`pagamento.feature`)
- ✅ Processamento de pagamento aprovado
- ✅ Processamento de pagamento recusado
- ✅ Processamento de pagamento cancelado
- ✅ Reprocessamento de notificações duplicadas

### 3. Gerenciamento de Status (`status-pedido.feature`)
- ✅ Transições de status do pedido
- ✅ Registro de histórico de mudanças
- ✅ Fluxo completo: RECEBIDO → EM_PREPARACAO → PRONTO → FINALIZADO
- ✅ Consulta de histórico de status

## 🏗️ Estrutura do Projeto

```
src/test/
├── java/br/com/postech/techchallange_order/bdd/
│   ├── config/
│   │   └── CucumberSpringConfiguration.java    # Configuração Spring + Cucumber
│   ├── context/
│   │   └── TestContext.java                     # Contexto compartilhado entre steps
│   ├── hooks/
│   │   └── CucumberHooks.java                   # Hooks @Before e @After
│   ├── steps/
│   │   ├── CheckoutSteps.java                   # Step definitions para checkout
│   │   ├── PagamentoSteps.java                  # Step definitions para pagamento
│   │   └── StatusPedidoSteps.java               # Step definitions para status
│   └── CucumberTestRunner.java                  # Runner principal
└── resources/
    ├── features/
    │   ├── checkout.feature                     # Cenários de checkout
    │   ├── pagamento.feature                    # Cenários de pagamento
    │   └── status-pedido.feature                # Cenários de status
    ├── application-test.yml                     # Configuração para testes
    └── cucumber.properties                      # Propriedades do Cucumber

```

## 🚀 Como Executar

### Executar todos os testes BDD

```bash
mvn test -Dtest=CucumberTestRunner
```

### Executar apenas testes unitários (excluindo BDD)

```bash
mvn test -Dtest=!CucumberTestRunner
```

### Executar todos os testes (unitários + BDD)

```bash
mvn test
```

### Gerar relatórios Cucumber

Após a execução, os relatórios ficam disponíveis em:
- **HTML**: `target/cucumber-reports/cucumber.html`
- **JSON**: `target/cucumber-reports/cucumber.json`

## 📊 Relatórios

### Visualizar relatórios HTML

Abra o arquivo no navegador:
```bash
# Windows
start target/cucumber-reports/cucumber.html

# Linux/Mac
open target/cucumber-reports/cucumber.html
```

## 🔧 Tecnologias Utilizadas

- **Cucumber Java** 7.18.1 - Framework BDD
- **Cucumber Spring** 7.18.1 - Integração com Spring
- **Cucumber JUnit Platform** 7.18.1 - Execução via JUnit 5
- **Spring Boot Test** - Contexto de testes
- **Mockito** - Mocks e stubs
- **JUnit 5** - Assertions e execução

## 📝 Padrões e Boas Práticas Implementadas

### 1. **Gherkin em Português**
```gherkin
# language: pt
Funcionalidade: Processar Checkout de Pedidos
  Como um cliente
  Quero realizar o checkout do meu pedido
  Para finalizar minha compra
```

### 2. **Page Object Pattern adaptado para BDD**
- `TestContext`: Compartilha estado entre steps
- Step Definitions isoladas por funcionalidade

### 3. **Hooks para Setup/Teardown**
```java
@Before
public void beforeScenario(Scenario scenario)

@After
public void afterScenario(Scenario scenario)
```

### 4. **Mocks configurados corretamente**
- Uso de `@MockBean` para injeção de dependências mockadas
- Reset de mocks entre cenários

### 5. **Data Tables para dados tabulares**
```gherkin
E o cliente possui os seguintes produtos no carrinho:
  | idProduto | quantidade | precoUnitario |
  | 1         | 2          | 15.50         |
  | 2         | 1          | 25.00         |
```

### 6. **Scenario Outline para testes parametrizados**
```gherkin
Esquema do Cenário: Fluxo completo de status do pedido
  Dado que o pedido possui status "<statusAtual>"
  Quando atualizar o status do pedido para "<novoStatus>"
  Então o status deve ser atualizado com sucesso

  Exemplos:
    | statusAtual    | novoStatus     |
    | RECEBIDO       | EM_PREPARACAO  |
    | EM_PREPARACAO  | PRONTO         |
```

## 🎭 Executando Cenários Específicos

### Por tag (se adicionar tags nos features)
```bash
mvn test -Dcucumber.filter.tags="@checkout"
```

### Por feature específico
```bash
mvn test -Dcucumber.features="src/test/resources/features/checkout.feature"
```

## 🐛 Debug

Para executar com logs detalhados:

```bash
mvn test -Dtest=CucumberTestRunner -Dlogging.level.io.cucumber=DEBUG
```

## ✅ Checklist de Implementação

- [x] Dependências Cucumber adicionadas ao POM
- [x] Estrutura de diretórios BDD criada
- [x] Features em Gherkin (pt-BR) implementadas
- [x] Step Definitions completas
- [x] Configuração Spring + Cucumber
- [x] Hooks para setup/teardown
- [x] TestContext para compartilhar estado
- [x] Mocks configurados corretamente
- [x] Maven Surefire configurado
- [x] Relatórios Cucumber habilitados
- [x] Profile de teste separado
- [x] Documentação completa

## 🔍 Troubleshooting

### Problema: Testes não executam
**Solução**: Verifique se o `CucumberTestRunner` está no pacote correto e se as dependências foram baixadas.

### Problema: Steps não encontrados
**Solução**: Verifique o `@ConfigurationParameter(key = GLUE_PROPERTY_NAME)` no Runner.

### Problema: Contexto Spring não carrega
**Solução**: Certifique-se de que `@CucumberContextConfiguration` está presente na classe de configuração.

## 📚 Recursos Adicionais

- [Documentação Cucumber](https://cucumber.io/docs/cucumber/)
- [Cucumber com Spring Boot](https://cucumber.io/docs/cucumber/api/#spring)
- [Gherkin Reference](https://cucumber.io/docs/gherkin/reference/)

---

**Autor**: Tech Challenge Team  
**Data**: Janeiro 2026  
**Versão**: 1.0.0

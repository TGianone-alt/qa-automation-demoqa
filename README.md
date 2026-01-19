QA Automation – DemoQA

Automação de Testes com Java + RestAssured + Selenium + Cucumber

Visão Geral
Este projeto contempla testes de API REST aplicados sobre a Book Store API do DemoQA, utilizando Java 11, RestAssured e JUnit 5.

O objetivo desta camada é validar fluxos completos de negócio, garantindo que os endpoints funcionem corretamente de forma integrada, sem dependência de interface gráfica.

Swagger oficial da API: https://demoqa.com/swagger/

Objetivo dos Testes de API
Automação de APIs REST com RestAssured

Validação de regras de negócio e contratos

Execução de fluxo end-to-end realista

Separação clara entre clients, models e tests

Boas práticas aplicadas a projetos reais

Fluxo Automatizado – Book Store API
O cenário de API cobre o seguinte fluxo completo:

1 - Criar usuário POST /Account/v1/User

Geração dinâmica de username e password

Validação de status code e payload

2 - Gerar token de autenticação POST /Account/v1/GenerateToken

Validação de token não nulo e não vazio

3 - Validar autorização do usuário POST /Account/v1/Authorized

Confirmação de que o usuário está autorizado

4 - Listar livros disponíveis GET /BookStore/v1/Books

Garantia de que há livros disponíveis no sistema

5 - Reservar dois livros POST /BookStore/v1/Books

Seleção dinâmica de ISBNs

Uso de token JWT no header Authorization

6 - Buscar detalhes do usuário GET /Account/v1/User/{userId}

Validação do userId

Validação da quantidade de livros reservados

Confirmação dos ISBNs associados ao usuário

Estrutura dos Testes de API
client: encapsula chamadas REST e evita duplicação

model: representa contratos da API

tests: contém apenas lógica de teste e asserts

Execução dos Testes de API
Executar apenas o teste de API:

mvn -Dtest=BookStoreApiFlowTest test

Ou executar toda a suíte (UI + API):

mvn test

Observação: testes de API não possuem saída visual. O sucesso é indicado por BUILD SUCCESS.

Testes UI – Automação Web com Selenium + Cucumber
Visão Geral

Este projeto tem como objetivo demonstrar boas práticas de automação de testes UI utilizando Java 11, Selenium WebDriver e Cucumber (BDD), aplicadas sobre o site DemoQA.

O foco principal é:

Automação funcional end-to-end

Escrita de cenários em BDD

Uso de Page Object Model

Testes estáveis, legíveis e manuteníveis

Decisões técnicas alinhadas à realidade de projetos reais

O projeto cobre múltiplos componentes do DemoQA, incluindo formulários, tabelas dinâmicas, múltiplas janelas, drag and drop e progress bar.

Tecnologias Utilizadas
Java 11

Maven

Selenium WebDriver

RestAssured

Cucumber (JUnit Platform)

JUnit 5

WebDriverManager

Page Object Model (POM)

BDD (Behavior Driven Development)

Observações Importantes sobre a Estrutura
Pages ficam em src/main/java para permitir reutilização

Steps ficam em src/test/java, pois são específicos de teste

Features ficam em src/test/resources/features

Arquivos de upload ficam em src/test/resources/files

Properties do Cucumber ficam em src/test/resources/properties

Projeto segue o Single Responsibility Principle

Padrões e Boas Práticas Aplicadas
Page Object Model

Esperas explícitas (WebDriverWait)

JavaScript click como fallback quando necessário

Remoção de banners e footers do DemoQA

Cenários independentes

Tags para execução seletiva

BDD legível (negócio + comportamento)

Cenários Automatizados (UI)
Practice Form (@practiceform)
Preenchimento completo com dados randomizados

Upload de arquivo (upload.txt)

Envio do formulário

Validação dos dados no modal

Fechamento do modal antes da validação final

Browser Windows (@browserwindows)
Abertura de nova janela

Troca de contexto entre janelas

Validação do conteúdo exibido

Web Tables (@webtables)
Create

Criação de 12 registros

Exclusão de todos os registros ao final

Validação de que não existem mais

Update

Criação de registro

Edição de campos específicos

Validação dos dados atualizados

Delete

Criação de registro

Exclusão

Validação da remoção

Sortable (@sortable)
Acesso direto à página

Reordenação via drag and drop

Validação da ordem decrescente (6 → 1)

Progress Bar (@progressbar)
Início da progress bar

Interrupção antes de 25%

Validação do valor ao parar

Retomada até 100%

Validação de conclusão sem depender do botão Reset

Decisão técnica tomada devido à instabilidade do comportamento do botão Reset no DemoQA.

Como Executar os Testes
Executar todos os testes:

mvn test

Executar por tag:

mvn test -Dcucumber.filter.tags=@practiceform mvn test -Dcucumber.filter.tags=@webtables mvn test -Dcucumber.filter.tags=@sortable mvn test -Dcucumber.filter.tags=@browserwindows mvn test -Dcucumber.filter.tags=@progressbar

Configuração do Ambiente
Pré-requisitos:

Java 11+

Maven

Google Chrome

O WebDriverManager gerencia automaticamente a versão do ChromeDriver.

Decisões Técnicas Importantes
URLs acessadas diretamente para evitar instabilidade da home

Progress Bar não depende de Reset

CRUD do WebTables limpa dados criados

Esperas inteligentes para renderizações dinâmicas

Uso pontual de JavaScriptExecutor

Decisões baseadas em experiência prática de automação real

👤 Autor

Thiago Gianone QA Automation Engineer

Projeto desenvolvido com foco em qualidade, organização, clareza e boas práticas, cobrindo automação UI e API em um único repositório.

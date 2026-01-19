@webtables
Feature: Web Tables - CRUD e validacoes

  Background:
    Given que estou na tela de Web Tables

  @create
  Scenario: Criar 12 registros e validar inclusao
    When adiciono 12 registros aleatorios
    And deleto os 12 registros criados
    Then devo visualizar 12 registros removidos da tabela

  @update
  Scenario: Editar um registro e validar alteracao
    When adiciono 1 registro aleatorio
    And edito o registro criado
    Then o registro deve refletir os dados atualizados

  @delete
  Scenario: Deletar um registro e validar remocao
    When adiciono 1 registro aleatorio
    And deleto o registro criado
    Then o registro nao deve mais existir na tabela

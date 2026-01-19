@sortable
Feature: Sortable - Ordenação por drag and drop

  Scenario: Ordenar itens do Sortable em ordem decrescente
    Given que estou na tela de Sortable
    When organizo os itens do Sortable em ordem decrescente
    Then devo ver os itens na ordem decrescente

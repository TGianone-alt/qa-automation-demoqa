@browserwindows
Feature: Browser Windows - New Window

  Scenario: Abrir uma nova janela ao clicar em New Window e validar conteúdo
    Given que estou na tela de Browser Windows
    When clico em New Window
    Then devo ver a mensagem "This is a sample page" na nova janela
